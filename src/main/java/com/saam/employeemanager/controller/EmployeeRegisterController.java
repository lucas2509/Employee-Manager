package com.saam.employeemanager.controller;

import com.saam.employeemanager.model.AccountModel;
import com.saam.employeemanager.model.Employee;
import com.saam.employeemanager.model.EmployeeModel;
import com.saam.employeemanager.view.EmployeeRegisterFrame;
import java.sql.Date;
import java.sql.SQLException;
import javax.swing.JOptionPane;

// Controlador responsável pela inserção/edição dos funcionários (implementação singleton)
public class EmployeeRegisterController {
    // Instância única do singleton
    private static EmployeeRegisterController instance;
    
    // Frame de interface do usuário para os funcionários
    private final EmployeeRegisterFrame employeeRegisterFrame;
    
    // Modelo de dados para manipulação dos funcionários
    private final EmployeeModel employeeModel;
    
    // Modelo de dados para manipulação dos funcionários
    private Employee employee;

    // Construtor privado para garantir que seja um singleton
    private EmployeeRegisterController() {
        this.employeeRegisterFrame = new EmployeeRegisterFrame();
        this.employeeModel = new EmployeeModel();
    }
    
    // Método estático para obter a instância única do singleton
    public static synchronized EmployeeRegisterController getInstance() {
        if (instance == null) {
            instance = new EmployeeRegisterController();
        }
        return instance;
    }
    
    // Inicializador do controller
    public void init() {
        // Configura a tela principal 
        java.awt.EventQueue.invokeLater(() -> {
            // Exibe a tela principal
            employeeRegisterFrame.setVisible(true);
        });
        
        // Carrega as informações do funcionário caso esteja selecionado
        loadEmployeeData();
        
        // Atribuição do botão "Registrar Funcionário" à função handleRegisterEmployee
        employeeRegisterFrame.setRegisterButtonListener((e) -> {
            handleRegisterEmployee();
        });
    }
    
    // Método que chama a função responsável por preencher os dados do funcionário na tela
    public void loadEmployeeData(){
        employeeRegisterFrame.loadEmployeeData(employee);
    }
    
    // Setter do atributo employee
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    // Método responsável pela criação ou atualização de um funcionário
    private void handleRegisterEmployee(){
        try {
            // Caso seja uma edição de dados do funcionário, há então a atualização do registro
            if(employee != null){
                //Atualiza o atributo employee com os dados do funcionário preenchido
                collectEmployeeData();

                // Realiza a atualização do funcionário no banco de dados
                if(employeeModel.updateEmployee(employee)){
                    JOptionPane.showMessageDialog(null, "Funcionário alterado com sucesso",
                            "Informação", JOptionPane.INFORMATION_MESSAGE);
                    employeeRegisterFrame.dispose();
                    EmployeeController.getInstance().refreshEmployeeTable();
                }
                else JOptionPane.showMessageDialog(null, "Falha na atualização, tente novamente",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

            // Caso seja o registro de um novo funcionário, há então o registro do funcionário
            else {
                //Atualiza o atributo employee com os dados do funcionário preenchido
                collectEmployeeData();

                // Realiza o registro do funcionário
                if(employeeModel.createEmployee(employee)){
                    JOptionPane.showMessageDialog(null, "Funcionário registrado com sucesso",
                            "Informação", JOptionPane.INFORMATION_MESSAGE);
                    employeeRegisterFrame.dispose();
                    EmployeeController.getInstance().refreshEmployeeTable();
                }
                else JOptionPane.showMessageDialog(null, "Falha no cadastro, tente novamente",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (SQLException e) {
            handleError("Houve um problema ao excluir o funcionário \n" +e.getMessage());
        }
    }
    
    // Método que atualiza o atributo employee de acordo com os dados no frame
    private void collectEmployeeData(){
        int accountId = AccountModel.getInstance().getLoggedInAccount().getId();
        String name = employeeRegisterFrame.getName();
        Date date = Date.valueOf(employeeRegisterFrame.getAdmissionDate());
        Float salaryValue = Float.valueOf(employeeRegisterFrame.getSalaryValue());
        Boolean status = employeeRegisterFrame.getStatus();
        
        if (employee != null) {
            employee.setAccountId(accountId);
            employee.setName(name);
            employee.setAdmissionDate(date);
            employee.setSalaryValue(salaryValue);
            employee.setStatus(status); 
        }
        else employee = new Employee(-1,accountId,name,date,salaryValue,status);

    }
    
    //Exibe ao usuário o erro ocorrido
    private void handleError(String message) {
        JOptionPane.showMessageDialog(null, message,
                "Alerta de Erro!", JOptionPane.ERROR_MESSAGE);
    }
}

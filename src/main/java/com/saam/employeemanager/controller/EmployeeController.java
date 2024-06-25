package com.saam.employeemanager.controller;

import com.saam.employeemanager.model.Employee;
import com.saam.employeemanager.model.EmployeeModel;
import com.saam.employeemanager.view.EmployeeFrame;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

// Controlador responsável pelos funcionários (implementação singleton)
public class EmployeeController {
    // Instância única do singleton
    private static EmployeeController instance;
    
    // Frame de interface do usuário para os funcionários
    private final EmployeeFrame employeeFrame;
    
    // Modelo de dados para manipulação dos funcionários
    private final EmployeeModel employeeModel;
    
    // Construtor privado para garantir que seja um singleton
    private EmployeeController() {
        this.employeeFrame = new EmployeeFrame();
        this.employeeModel = new EmployeeModel();
    }
    
    // Método estático para obter a instância única do singleton
    public static synchronized EmployeeController getInstance() {
        if (instance == null) {
            instance = new EmployeeController();
        }
        return instance;
    }
    
    // Inicializador do controller
    public void init() {
        // Configura a tela principal 
        java.awt.EventQueue.invokeLater(() -> {
            // Exibe a tela principal
            employeeFrame.setVisible(true);
            
            // Atualiza a tabela de funcionários
            refreshEmployeeTable();
        });
        
        // Atribuição do botão "Excluir Funcionário" à função handleDeleteEmployee
        employeeFrame.setDeleteEmployeeButtonListener((e) -> {
            handleDeleteEmployee();
        });
        
        // Atribuição do botão "Novo Funcionário" à função handleUpdateEmployee
        employeeFrame.setNewEmployeeButtonListener((e) -> {
            handleRegisterEmployee();
        });
        
        // Atribuição do botão "Editar Funcionário" à função handleUpdateEmployee
        employeeFrame.setEditEmployeeButtonListener((e) -> {
            handleOpenUpdateEmployee();
        });
    }
    
    // Método responsável por atualizar a tabela com os registros de funcionários
    public void refreshEmployeeTable() {
        try {
            // Obtém a lista de funcionários do modelo
            List<Employee> employees = employeeModel.getAllEmployees();
            
            // Exibe a lista na interface do usuário
            employeeFrame.displayEmployees(employees);
        } 
        catch (SQLException e) {
            handleError("Houve um problema na obtenção de dados referente a funcionários \n" +e.getMessage());
        }
    }
    
    // Método responsável por lidar com a atualização de funcionários
    private void handleOpenUpdateEmployee() {
        try{
            // Obtém a linha selecionada
            int selectedRow = employeeFrame.getSelectedEmployeeRow();

            if (selectedRow >= 0) {
               // Obtém o ID do funcionário
               int employeeId = employeeFrame.getEmployeeIdAt(selectedRow);

               // Obtém o registro do funcionário
               Employee employee = employeeModel.getEmployeeByID(employeeId);

               // Seleciona o funcionário para o controlador de registro
               EmployeeRegisterController.getInstance().setEmployee(employee);

               // Inicia o controlador
               EmployeeRegisterController.getInstance().init();
            }
        }
        catch (SQLException e) {
            handleError("Houve um problema ao carregar dados do funcionário \n" +e.getMessage());
        }
        
    }
    
    // Método responsável pela criação de um funcionário
    private void handleRegisterEmployee(){
       // Seta como null o atributo employee para inserir um novo registro
       EmployeeRegisterController.getInstance().setEmployee(null);
            
       // Inicia o controlador
       EmployeeRegisterController.getInstance().init();
    }
    
    // Método reposável pela exclusão de um funcionário
    public void handleDeleteEmployee() {
        try {
            // Obtém a linha selecionada
            int selectedRow = employeeFrame.getSelectedEmployeeRow();

            if (selectedRow >= 0) {
                // Obtém o nome do funcionário para exibir na confirmação
                String employeeName = employeeFrame.getEmployeeNameAt(selectedRow);

                // Solicita confirmação do usuário
                int confirmation = JOptionPane.showConfirmDialog(employeeFrame, 
                        "Tem certeza de que deseja excluir o funcionário " + employeeName + "?", 
                        "Confirmar Exclusão", 
                        JOptionPane.YES_NO_OPTION);

                if (confirmation == JOptionPane.YES_OPTION) {
                    // Obtém o ID do funcionário
                    int employeeId = employeeFrame.getEmployeeIdAt(selectedRow);

                    // Chama o método do modelo para deletar o funcionário
                    boolean deleted = employeeModel.deleteEmployee(employeeId);

                    // Se a exclusão for bem-sucedida, atualiza a tabela
                    if (deleted) {
                        refreshEmployeeTable();
                        JOptionPane.showMessageDialog(employeeFrame, 
                                "Funcionário excluído com sucesso", 
                                "Informação", 
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(employeeFrame, 
                                "Falha ao excluir funcionário", 
                                "Erro", 
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        catch (SQLException e) {
            handleError("Houve um problema ao excluir o funcionário \n" +e.getMessage());
        }
    }

    //Exibe ao usuário o erro ocorrido
    private void handleError(String message) {
        JOptionPane.showMessageDialog(null, message,
                "Alerta de Erro!", JOptionPane.ERROR_MESSAGE);
    }
}

package com.saam.employeemanager.controller;

import com.saam.employeemanager.view.AccountLoginFrame;
import com.saam.employeemanager.view.AccountLoginFrame;
import com.saam.employeemanager.model.AccountModel;
import com.saam.employeemanager.view.AccountRegisterFrame;
import java.sql.SQLException;
import javax.swing.JOptionPane;

// Controlador responsável pelo login de contas (implementação singleton)
public class AccountLoginController {
    // Instância única do singleton
    private static AccountLoginController instance;

    // Frame de Login responsável pela interface dos dados das credenciais.
    private final AccountLoginFrame accountLoginFrame;
    
    // Frame de Cadastro responsável pela interface dos dados de cadastro.
    private final AccountRegisterFrame accountRegisterFrame;
    
    // Modelo de conta usado para realizar a autenticação do usuário
    private final AccountModel accountModel;

    // Construtor privado para garantir que seja um singleton
    private AccountLoginController() {
        this.accountLoginFrame = new AccountLoginFrame();
        this.accountRegisterFrame = new AccountRegisterFrame();
        this.accountModel = AccountModel.getInstance();
    }

    // Método estático para obter a instância única do singleton
    public static synchronized AccountLoginController getInstance() {
        if (instance == null) {
            instance = new AccountLoginController();
        }
        return instance;
    }

    // Inicializador do controller
    public void init() {
        // Configura e exibe a tela principal
        java.awt.EventQueue.invokeLater(() -> {
            accountLoginFrame.setVisible(true);
            
        });
        
        // Atribuição do botão ENTRAR à função handleLogin
        accountLoginFrame.setLoginButtonListener((e) -> {
            handleLogin();
        });
        
        // Atribuição do botão Novo Usuário à função handleOpenRegister
        accountLoginFrame.setRegisterButtonListener((e) -> {
            handleOpenRegister();
        });
        
        // Atribuição do botão REGISTRAR à função handleRegister
        accountRegisterFrame.setRegisterButtonListener((e) -> {
            handleRegister();
        });
    }
    
    // Método responsável por realizar o login do usuário e exibir o resultado da autenticação
    private void handleLogin() {
        String email = accountLoginFrame.getEmail();
        String password = accountLoginFrame.getPassword();

        try {
            // Realiza a autenticação do usuário
            if (accountModel.authenticate(email, password)) {
                JOptionPane.showMessageDialog(null, "Usuário logado com sucesso",
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
                accountLoginFrame.dispose();

                // Inicializa o EmployeeController após o login bem-sucedido
                EmployeeController.getInstance().init();
            } 
            else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorreta",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
        catch (SQLException e){
            handleError("Erro ao realizar a autentificação \n" +e.getMessage());
        }
    }
    
    // Método responsável por abrir a janela de cadastro
    private void handleOpenRegister() {
        accountRegisterFrame.setVisible(true);
    }
    
    // Método responsável por realizar o cadastro do usuário
    private void handleRegister() {
        String username = accountRegisterFrame.getUsername();
        String email = accountRegisterFrame.getEmail();
        String password = accountRegisterFrame.getPassword();
        String passwordConfirmed = accountRegisterFrame.getPasswordConfirmed();

        try {
            // Verifica se as duas senhas são iguais
            if (password.equals(passwordConfirmed)) {
                // Realiza o registro do usuário
                if (accountModel.register(username, email, password)) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso",
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
                    accountRegisterFrame.dispose();
                } 
                else {
                    JOptionPane.showMessageDialog(null, "Falha no cadastro, tente novamente",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "As duas senhas devem ser iguais",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
        catch(SQLException e){
            handleError("Houve um problema no sistema de autentificação \n" +e.getMessage());
        }
    }
    
    //Exibe ao usuário o erro ocorrido
    private void handleError(String message) {
        JOptionPane.showMessageDialog(null, message,
                "Alerta de Erro!", JOptionPane.ERROR_MESSAGE);
    }
}

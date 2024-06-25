package com.saam.employeemanager.model;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.codec.binary.Hex;

import org.apache.commons.codec.digest.DigestUtils;

// Classe Singleton responsável por gerenciar dados os usuários.
public class AccountModel {
    // Instancia Singleton da classe
    private static AccountModel instance;
    
    // Conta de usuário logada na aplicação
    private Account loggedInAccount;
    
    private AccountModel() {
        // Constructor privado para evitar instanciação
    }
    
    // Obtém a instância única da sessão do usuário
    public static synchronized AccountModel getInstance() {
        if (instance == null) {
            instance = new AccountModel();
        }
        return instance;
    }
    
    // Método para autenticar um usuário
    public boolean authenticate(String email, String password) throws SQLException{
        // Query que para obtenção de informações do usuário de acordo com email
        String query = "SELECT id, username, password, salt FROM account WHERE email = ?";
        
        // Executa a conexão com o banco de dados
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            
            // Obtém o resultado da consulta
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String storedPassword = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                
                // Calcula o hash SHA-256 da senha fornecida com o salt armazenado no banco de dados
                String hashedPassword = DigestUtils.sha256Hex(password + salt);
                
                // Compara o hash calculado com o hash armazenado no banco de dados
                // Login bem-sucedido
                if (storedPassword.equals(hashedPassword)) {
                    this.loggedInAccount =  new Account(id, username, email, storedPassword, salt);
                    return true;
                }
                // Senha incorreta
                else  return false; 
            } 
            // Usuário não encontrado
            else return false; 
        } 
        catch (SQLException e) {
            e.printStackTrace();
            throw e; 
        }
    }

    // Método para registrar o usuário
    public boolean register(String username, String email, String password) throws SQLException{
        // Query que para inserção de um registro de um usuário
        String query = "INSERT INTO account (username, email, password, salt) VALUES (?, ?, ?, ?)";
        
        // Executa a conexão com o banco de dados
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            SecureRandom random = new SecureRandom();
            byte[] randomSalt = new byte[16];
            random.nextBytes(randomSalt);
            // Obtém um valor aleatório para registro do valor salt da criptografia
            String salt = Hex.encodeHexString(randomSalt);
            
            // Obtém a senha criptografada
            String hashedPassword = DigestUtils.sha256Hex(password + salt);
            
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, hashedPassword);
            statement.setString(4, salt);
            
            // Executa a inserção
            statement.executeUpdate();
        } 
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return true;
    }
    
    // Obtém o usuário logado na aplicação
    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    // Desassocia o usuário a aplicação
    public void logout() {
        loggedInAccount = null;
    }
}

package com.saam.employeemanager.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Classe responsável por gerenciar dados dos funcionários.
public class EmployeeModel {
    // Método para obter uma lista de todos os funcionários de acordo com o usuário logado
    public static List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        
        // Query que para dos registros de funcionários de acordo com o usuário logado
        String sql = "SELECT * FROM employee WHERE account_id = ?";

        // Executa a conexão com o banco de dados
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
   
            statement.setInt(1, AccountModel.getInstance().getLoggedInAccount().getId());
            ResultSet resultSet = statement.executeQuery();
                 
            // Obtém o resultado da consulta
            while (resultSet.next()) {
                    Employee employee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getInt("account_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("admission_date"),
                        resultSet.getFloat("salary_value"),
                        resultSet.getBoolean("status")
                    );
                    employees.add(employee);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return employees;
    }

    // Método para obter um funcionário a partir da ID
    public Employee getEmployeeByID(int employeeId) throws SQLException{
        // Query que para obtenção do registro de funcionários de acordo com a ID
        String sql = "SELECT * FROM employee WHERE id = ?";

        // Executa a conexão com o banco de dados
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            // Obtém o resultado da consulta
            if (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getInt("account_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("admission_date"),
                        resultSet.getFloat("salary_value"),
                        resultSet.getBoolean("status")
                );
                return employee;
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        // Retorna null se não encontrar o funcionário com o ID especificado
        return null; 
    }
    
    // Método para criar um novo funcionário no banco de dados
    public boolean createEmployee(Employee employee) throws SQLException{
        // Query para inserção de um novo funcionário
        String sql = "INSERT INTO employee (account_id, name, admission_date, salary_value, status) VALUES (?, ?, ?, ?, ?)";

        // Executa a conexão com o banco de dados
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employee.getAccountId());
            statement.setString(2, employee.getName());
            statement.setDate(3, employee.getAdmissionDate());
            statement.setDouble(4, employee.getSalaryValue());
            statement.setBoolean(5, employee.isStatus());

            // Insere o novo registro
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Método para atualizar um funcionário no banco de dados
    public boolean updateEmployee(Employee employee) throws SQLException{
        // Query que para atualização do registro de funcionário
        String sql = "UPDATE employee SET name = ?, admission_date = ?, salary_value = ?, status = ? WHERE id = ?";

        // Executa a conexão com o banco de dados
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, employee.getName());
            statement.setDate(2, employee.getAdmissionDate());
            statement.setDouble(3, employee.getSalaryValue());
            statement.setBoolean(4, employee.isStatus());
            statement.setInt(5, employee.getId());

            // Atualiza o registro
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Método para excluir um funcionário do banco de dados
    public boolean deleteEmployee(int employeeId) throws SQLException{
        // Query que para exclusão do registro de funcionário
        String sql = "DELETE FROM employee WHERE id = ?";

        // Executa a conexão com o banco de dados
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);

            // Deleta o registro
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}

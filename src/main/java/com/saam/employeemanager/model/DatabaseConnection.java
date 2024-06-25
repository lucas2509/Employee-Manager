package com.saam.employeemanager.model;

import com.saam.employeemanager.util.PropertyLoader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Classe responsável pela conexão com a Database
public class DatabaseConnection {
    // Conexão única (singleton) com a database
    private static Connection connection = null;
    
    // Atributo usado para armazenar as configurações do banco de dados
    private static Properties properties;

    // Método para obter uma conexão com a database.
    public static Connection getConnection() throws SQLException {
        // Verifica se a conexão atual já existe e está aberta
        if (connection != null && !connection.isClosed()) {
            return connection;
        }
        try {
            // Carrega as propriedades do arquivo
            properties = PropertyLoader.loadProperties();
            
            String url = properties.getProperty("jdbc.url");
            String user = properties.getProperty("jdbc.user");
            String password = properties.getProperty("jdbc.password");

            // Realiza uma nova conexão com a database
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao conectar ao banco de dados", e);
        }

        return connection;
    }
}

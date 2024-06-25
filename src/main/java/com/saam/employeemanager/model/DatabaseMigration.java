package com.saam.employeemanager.model;

import org.flywaydb.core.Flyway;

import com.saam.employeemanager.util.PropertyLoader;
import java.io.IOException;
import java.util.Properties;

// Classe responsável pela migração da Database
public class DatabaseMigration {
    // Atributo usado para armazenar as configurações do banco de dados
    private static Properties properties;

    // Método responsável por realizar a migração do banco de dados
    public static void migrateDatabase() throws IOException{
        try {
            // Carrega as propriedades do arquivo
            properties = PropertyLoader.loadProperties();

            String url = properties.getProperty("jdbc.url");
            String user = properties.getProperty("jdbc.user");
            String password = properties.getProperty("jdbc.password");
            
            Flyway flyway = Flyway.configure()
                    .dataSource(url, user, password)
                    .load();

           // Migração da database
           flyway.migrate();
        } 
        catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}

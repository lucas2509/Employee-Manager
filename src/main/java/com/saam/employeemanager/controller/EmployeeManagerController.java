package com.saam.employeemanager.controller;

import com.saam.employeemanager.model.DatabaseMigration;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.flywaydb.core.api.FlywayException;

//Controlador inicial responsável pela migração da database
public class EmployeeManagerController {
    //Inicializador do controller
    public void init() {
        try {
            // Executa a migração do banco de dados
            DatabaseMigration.migrateDatabase();

            // Inicializa o controlador de Login
            AccountLoginController.getInstance().init();
        } 
        catch (IOException | FlywayException | NullPointerException e) {
            if(e instanceof FlywayException) handleError("Erro ao realizar a migração da Database \n" +e.getMessage());
            if(e instanceof NullPointerException) handleError("Erro ao carregar o arquivo de configuração database.proporties \n" +e.getMessage());
        }
    }
    
    //Exibe ao usuário o erro causado
    private void handleError(String message) {
        JOptionPane.showMessageDialog(null, message,
                "Erro ao inicializar a aplicação", JOptionPane.ERROR_MESSAGE);
    }
}

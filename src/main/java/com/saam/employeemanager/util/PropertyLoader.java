package com.saam.employeemanager.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Classe utilitária responsável por carregar propriedades de um arquivo
public class PropertyLoader {
    // Nome do arquivo de propriedades
    private static final String PROPERTIES_FILE = "/database.properties";

    // Carrega as propriedades do arquivo especificado
    public static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        
        try (InputStream input = PropertyLoader.class.getResourceAsStream(PROPERTIES_FILE)) {
            props.load(input);
        }
        return props;
    }
}

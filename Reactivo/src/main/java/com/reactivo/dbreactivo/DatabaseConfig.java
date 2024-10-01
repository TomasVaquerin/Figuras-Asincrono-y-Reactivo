package com.reactivo.dbreactivo;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

public class DatabaseConfig {

    public static DatabaseConfig instance;

    private static ConnectionFactory connectionFactory;

    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    private DatabaseConfig() {
        Properties properties = new Properties();
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input == null) {
                throw new IOException("No se pudo cargar el archivo de propiedades");
            }
            properties.load(input);
            connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                    .option(DRIVER, "h2")
                    .option(PROTOCOL, "mem")  // mem significa que es en memoria
                    .option(DATABASE, "testdb")
                    .option(USER, properties.getProperty("r2dbc.username", "sa"))
                    .option(PASSWORD, properties.getProperty("r2dbc.password", "ad"))
                    .build());
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar el archivo de propiedades", e);
        }
    }

    public static ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }


}


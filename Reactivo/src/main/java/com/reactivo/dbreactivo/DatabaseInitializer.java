package com.asincrono.dbasincrono;


import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;

public class DatabaseInitializer {

    private final ConnectionFactory connectionFactory;

    public DatabaseInitializer() {
        this.connectionFactory = DatabaseConfig.getInstance().getConnectionFactory();
    }

    public Mono<Void> initialize() {
        dropTable();
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> {
                    return Mono.from(connection.createStatement(
                                    "CREATE TABLE IF NOT EXISTS figuras (" +
                                            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                                            "cod UUID UNIQUE DEFAULT RANDOM_UUID(), " +
                                            "my_id BIGINT GENERATED ALWAYS AS IDENTITY, " +
                                            "nombre VARCHAR(255) NOT NULL, " +
                                            "modelo ENUM('MARVEL', 'DISNEY', 'ANIME', 'DEPORTE', 'MUSICA', 'OTROS') NOT NULL, " +
                                            "precio DECIMAL(10,2), " +
                                            "fecha_lanzamiento DATE, " +
                                            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                                            "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)"
                            ).execute())
                            .doFinally(signal -> connection.close());
                }).then();
    }

    public Mono<Void> dropTable() {
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> {
                    return Mono.from(connection.createStatement(
                                    "DROP TABLE IF EXISTS figuras"
                            ).execute())
                            .doFinally(signal -> connection.close());
                }).then();
    }
}

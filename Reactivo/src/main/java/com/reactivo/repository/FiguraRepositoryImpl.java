package com.reactivo.repository;

import com.reactivo.dbreactivo.DatabaseConfig;
import com.reactivo.model.Figura;
import com.reactivo.model.Modelo;
import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public class FiguraRepositoryImpl implements FiguraRepository {

    private final ConnectionFactory connectionFactory;

    private final Logger logger = LoggerFactory.getLogger(FiguraRepositoryImpl.class);

    public FiguraRepositoryImpl(ConnectionFactory connectionFactory) {
        DatabaseConfig.getInstance();
        this.connectionFactory = DatabaseConfig.getConnectionFactory();
    }



    @Override
    public Flux<Figura> findAll() {
        String query = "SELECT * FROM figuras";
        logger.debug("(REPO)Buscando todas las figuras en la base de datos");

        return Flux.from(connectionFactory.create())
                .flatMap(connection -> {
                    return Flux.from(connection.createStatement(query).execute())
                            .flatMap(result -> result.map((row, rowMetadata) -> {
                                return new Figura(
                                        row.get("id", Long.class),
                                        UUID.fromString(row.get("cod", String.class)),
                                        row.get("my_id", Long.class),
                                        row.get("nombre", String.class),
                                        row.get("modelo", Modelo.class),
                                        row.get("precio", Double.class),
                                        row.get("fecha_lanzamiento", java.time.LocalDate.class),
                                        row.get("created_at", java.time.LocalDateTime.class),
                                        row.get("updated_at", java.time.LocalDateTime.class)
                                );
                            }))
                            .switchIfEmpty(Flux.empty())
                            .doFinally(signal -> connection.close());
                });
        }

    @Override
    public Mono<Figura> findById(Long id) {
        String query = "SELECT * FROM figuras WHERE id = $1";
        logger.debug("(REPO)Buscando la figura con id: " + id);

        return Mono.from(connectionFactory.create())
                .flatMap(connection ->
                        Mono.from(connection.createStatement(query)
                            .bind("$1", id)
                            .execute())
                            .flatMap(result -> Mono.from(result.map((row, rowMetadata) ->
                                    new Figura(
                                        row.get("id", Long.class),
                                        UUID.fromString(row.get("cod", String.class)),
                                        row.get("my_id", Long.class),
                                        row.get("nombre", String.class),
                                        row.get("modelo", Modelo.class),
                                        row.get("precio", Double.class),
                                        row.get("fecha_lanzamiento", java.time.LocalDate.class),
                                        row.get("created_at", LocalDateTime.class),
                                        row.get("updated_at", LocalDateTime.class)
                                )
                            )))
                            .switchIfEmpty(Mono.empty())
                            .doFinally(signal -> connection.close())
                );
    }


    @Override
    public Mono<Figura> save(Figura object) {
        logger.debug("(REPO)Guardando la figura: " + object.getNombre());
        String query = "INSERT INTO figuras (cod, my_id, nombre, modelo, precio, fecha_lanzamiento, created_at, updated_at)"
        + " VALUES ($1, $2, $3, $4, $5, $6, $7, $8)";
        return Mono.from(connectionFactory.create())
                .flatMap(connection ->
                    Mono.from(connection.createStatement(query)
                                    .bind("$1", object.getCod().toString())
                                    .bind("$2", object.getMyId())
                                    .bind("$3", object.getNombre())
                                    .bind("$4", object.getModelo())
                                    .bind("$5", object.getPrecio())
                                    .bind("$6", object.getFechaLanzamiento())
                                    .bind("$7", object.getCreatedAt())
                                    .bind("$8", object.getUpdatedAt())
                                    .execute())
                            .flatMap(result ->
                                    Mono.from(result.getRowsUpdated())
                                            .flatMap(rowsUpdated -> {
                                                if (rowsUpdated > 0) {
                                                    return Mono.just(object);
                                                } else {
                                                    return Mono.empty();
                                                }
                                            })
                            )
                            .doFinally(signal -> connection.close())
                );
    }

    @Override
    public Mono<Figura> update(Long aLong, Figura object) {
        return null;
    }

    @Override
    public Mono<Void> delete(Long aLong) {
        return null;
    }

    @Override
    public Flux<Figura> findAllByModelo(Modelo modelo) {
        logger.debug("(REPO) Buscando todas las figuras con modelo: " + modelo);
        String query = "SELECT * FROM figuras WHERE modelo = $1";

        return Mono.from(connectionFactory.create())
                .flatMapMany(connection ->
                        Flux.from(connection.createStatement(query)
                                .bind("$1", modelo)
                                .execute())
                                .flatMap(result ->
                                        Flux.from(result.map((row, rowMetadata) ->
                                        new Figura(
                                                row.get("id", Long.class),
                                                UUID.fromString(row.get("cod", String.class)),
                                                row.get("my_id", Long.class),
                                                row.get("nombre", String.class),
                                                row.get("modelo", Modelo.class),
                                                row.get("precio", Double.class),
                                                row.get("fecha_lanzamiento", java.time.LocalDate.class),
                                                row.get("created_at", LocalDateTime.class),
                                                row.get("updated_at", LocalDateTime.class)
                                        )
                                )))
                                .switchIfEmpty(Flux.empty())
                                .doFinally(signal -> connection.close())
                );
    }
}


package com.reactivo.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Figura {
    private Long id;
    private UUID cod;
    private Long myId;
    private String nombre;
    private Modelo modelo;
    private Double precio;
    private LocalDate fechaLanzamiento;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

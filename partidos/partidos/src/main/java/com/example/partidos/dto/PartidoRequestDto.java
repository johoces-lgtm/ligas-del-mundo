package com.example.partidos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PartidoRequestDto {

    @NotNull
    private Long ligaId;

    @NotNull
    private Long clubLocalId;

    @NotNull
    private Long clubVisitaId;

    @NotNull
    private Long estadioId;

    private String nombreLocal;

    private String nombreVisita;

    private Integer golesLocal;

    private Integer golesVisita;

    private String estado;

    private Integer temporada;
}
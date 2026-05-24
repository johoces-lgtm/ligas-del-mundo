package com.example.posiciones.dto;

import lombok.Data;

@Data
public class PartidoResponseDto {

    private Long id;

    private Long clubLocalId;

    private Long clubVisitaId;

    private String nombreLocal;

    private String nombreVisita;

    private Integer golesLocal;

    private Integer golesVisita;

    private String estado;
}
package com.example.posiciones.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PosicionResponseDto {

    private Long id;

    private Long clubId;

    private String nombreClub;

    private Integer puntos;

    private Integer partidosJugados;

    private Integer ganados;

    private Integer empatados;

    private Integer perdidos;

    private Integer golesFavor;

    private Integer golesContra;

    private Integer diferenciaGoles;

    private Integer temporada;

    private Long ligaId;
}
package com.example.posiciones.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PosicionRequestDto {
    @NotNull(message = "El ID del club es obligatorio")
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
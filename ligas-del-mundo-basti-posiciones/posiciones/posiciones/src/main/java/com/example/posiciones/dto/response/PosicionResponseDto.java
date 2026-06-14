package com.example.posiciones.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO de salida con el estado de posición mapeado")
public class PosicionResponseDto {
    
    @Schema(description = "ID único del registro de posición", example = "50")
    private Long id;
    
    @Schema(description = "ID del club", example = "1")
    private Long clubId;
    
    @Schema(description = "Nombre oficial del club", example = "Colo-Colo")
    private String nombreClub;
    
    @Schema(description = "Puntos en la tabla", example = "25")
    private Integer puntos;
    
    @Schema(description = "Partidos disputados", example = "10")
    private Integer partidosJugados;
    
    @Schema(description = "Victorias", example = "8")
    private Integer ganados;
    
    @Schema(description = "Empates", example = "1")
    private Integer empatados;
    
    @Schema(description = "Derrotas", example = "1")
    private Integer perdidos;
    
    @Schema(description = "Goles a favor", example = "20")
    private Integer golesFavor;
    
    @Schema(description = "Goles en contra", example = "5")
    private Integer golesContra;
    
    @Schema(description = "Diferencia neta", example = "15")
    private Integer diferenciaGoles;
    
    @Schema(description = "Año de competición", example = "2026")
    private Integer temporada;
    
    @Schema(description = "ID de la liga", example = "3")
    private Long ligaId;
}
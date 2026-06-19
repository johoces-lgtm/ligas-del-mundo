package com.example.posiciones.dto.request;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO de entrada para registrar estadísticas de un club")
public class PosicionRequestDto {
    
    @NotNull(message = "El ID del club es obligatorio")
    @Schema(description = "Identificador del club (Debe existir en Microservicio Clubes)", example = "1")
    private Long clubId;
    
    @Schema(description = "Nombre opcional del club", example = "Colo-Colo")
    private String nombreClub;
    
    @Schema(description = "Puntos totales acumulados", example = "25")
    private Integer puntos;
    
    @Schema(description = "Cantidad de partidos jugados", example = "10")
    private Integer partidosJugados;
    
    @Schema(description = "Partidos ganados", example = "8")
    private Integer ganados;
    
    @Schema(description = "Partidos empatados", example = "1")
    private Integer empatados;
    
    @Schema(description = "Partidos perdidos", example = "1")
    private Integer perdidos;
    
    @Schema(description = "Goles marcados a favor", example = "20")
    private Integer golesFavor;
    
    @Schema(description = "Goles recibidos en contra", example = "5")
    private Integer golesContra;
    
    @Schema(description = "Cálculo de diferencia de goles", example = "15")
    private Integer diferenciaGoles;
    
    @Schema(description = "Año de la temporada actual", example = "2026")
    private Integer temporada;
    
    @Schema(description = "ID de la liga asociada", example = "3")
    private Long ligaId;
}
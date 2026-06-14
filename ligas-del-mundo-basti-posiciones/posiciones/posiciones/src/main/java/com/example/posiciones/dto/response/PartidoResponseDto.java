package com.example.posiciones.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO que mapea la información de un partido consumido desde el microservicio externo")
public class PartidoResponseDto {
    
    @Schema(description = "ID del partido", example = "101")
    private Long id;
    
    @Schema(description = "ID del club local", example = "1")
    private Long clubLocalId;
    
    @Schema(description = "ID del club visitante", example = "2")
    private Long clubVisitaId;
    
    @Schema(description = "Nombre del equipo local", example = "Colo-Colo")
    private String nombreLocal;
    
    @Schema(description = "Nombre del equipo visitante", example = "Universidad de Chile")
    private String nombreVisita;
    
    @Schema(description = "Goles anotados por el local", example = "2")
    private Integer golesLocal;
    
    @Schema(description = "Goles anotados por la visita", example = "1")
    private Integer golesVisita;
    
    @Schema(description = "Estado actual del encuentro", example = "FINALIZADO")
    private String estado;
}
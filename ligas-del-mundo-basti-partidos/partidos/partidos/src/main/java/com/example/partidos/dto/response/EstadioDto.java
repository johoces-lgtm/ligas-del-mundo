package com.example.partidos.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Esquema de respuesta obtenido del microservicio de estadios")
public class EstadioDto {

    @Schema(description = "Identificador del estadio", example = "5")
    private Long id;
    
    @Schema(description = "Nombre del complejo deportivo", example = "Santiago Bernabéu")
    private String nombre;
}
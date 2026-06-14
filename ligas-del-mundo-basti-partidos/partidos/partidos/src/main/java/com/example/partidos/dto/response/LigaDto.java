package com.example.partidos.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Esquema de respuesta obtenido del microservicio de ligas")
public class LigaDto {
    
    @Schema(description = "Identificador de la liga", example = "3")
    private Long id;
    
    @Schema(description = "Nombre de la competición", example = "LaLiga")
    private String nombre;
    
    @Schema(description = "País sede del torneo", example = "España")
    private String pais;
    
    @Schema(description = "Ruta web del logo oficial", example = "http://localhost/logo-laliga.png")
    private String logoUrl;
}
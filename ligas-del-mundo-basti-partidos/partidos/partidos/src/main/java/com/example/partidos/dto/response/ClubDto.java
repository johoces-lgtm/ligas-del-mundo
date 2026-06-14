package com.example.partidos.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Esquema reflejo del club obtenido del microservicio de clubes")
public class ClubDto {
    
    @Schema(description = "Identificador del club", example = "1")
    private Long id;
    
    @Schema(description = "Nombre de la institución", example = "Real Madrid")
    private String nombre;
    
    @Schema(description = "Ruta URL del escudo", example = "http://localhost/escudo-madrid.png")
    private String logoUrl;
    
    @Schema(description = "Año de fundación", example = "1902")
    private Integer anioFundacion;
    
    @Schema(description = "Nombre de su estadio principal", example = "Santiago Bernabéu")
    private String estadioNombre;
    
    @Schema(description = "ID de la liga asociada", example = "3")
    private Long ligaId;
}
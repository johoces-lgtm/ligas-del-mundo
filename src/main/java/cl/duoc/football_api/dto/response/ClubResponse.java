package cl.duoc.football_api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO de respuesta simplificado que expone los datos de identificación básicos de un Club local")
public class ClubResponse {
    
    @Schema(description = "ID único del club almacenado en la Base de Datos", example = "33")
    private Long id;
    
    @Schema(description = "Nombre oficial del equipo", example = "Manchester United")
    private String nombre;
}
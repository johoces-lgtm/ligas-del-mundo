package cl.duoc.football_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO utilizado por el Orquestador para enviar la información de una Liga al microservicio local")
public class LigaRequest {
    
    @Schema(description = "ID oficial de la liga provisto por la API externa", example = "39")
    private Long id;
    
    @Schema(description = "Nombre oficial de la competición", example = "Premier League")
    private String nombre;
    
    @Schema(description = "País de procedencia de la liga", example = "England")
    private String pais;
    
    @Schema(description = "URL del logo oficial de la liga", example = "https://media.api-sports.io/football/leagues/39.png")
    private String logoUrl;
}
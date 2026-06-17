package cl.duoc.football_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO utilizado para transmitir los datos de un Club e inyectarlos en el microservicio local")
public class ClubRequest {
    
    @Schema(description = "ID oficial del equipo provisto por la API externa", example = "529")
    private Long id;
    
    @Schema(description = "Nombre oficial del club de fútbol", example = "FC Barcelona")
    private String nombre;
    
    @Schema(description = "URL del escudo o logo oficial del club", example = "https://media.api-sports.io/football/teams/529.png")
    private String logoUrl;
    
    @Schema(description = "Año cronológico en el que fue fundado el club", example = "1899")
    private Integer anioFundacion;
    
    @Schema(description = "Nombre del recinto deportivo o estadio principal", example = "Camp Nou")
    private String estadioNombre;
    
    @Schema(description = "ID de la liga a la que pertenece este club", example = "39")
    private Long ligaId;
}
package cl.duoc.football_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO de transferencia masiva para registrar la ficha de un Futbolista en el sistema local")
public class JugadorRequest {
    
    @Schema(description = "ID oficial del jugador provisto por la API externa", example = "154")
    private Long id;
    
    @Schema(description = "Nombre corto o deportivo del jugador", example = "L. Messi")
    private String nombre;
    
    @Schema(description = "Primer nombre oficial del futbolista", example = "Lionel")
    private String primerNombre;
    
    @Schema(description = "Apellido oficial del futbolista", example = "Messi")
    private String apellido;
    
    @Schema(description = "Edad cronológica actual", example = "38")
    private Integer edad;
    
    @Schema(description = "Nacionalidad o país de origen", example = "Argentina")
    private String nacionalidad;
    
    @Schema(description = "URL directa a la foto oficial del perfil del jugador", example = "https://media.api-sports.io/football/players/154.png")
    private String fotoUrl;
    
    @Schema(description = "Posición táctica estándar dentro de la cancha", example = "Attacker")
    private String posicion;
    
    @Schema(description = "ID del club local al que se asociará la ficha del jugador", example = "33")
    private Long clubId; 
}
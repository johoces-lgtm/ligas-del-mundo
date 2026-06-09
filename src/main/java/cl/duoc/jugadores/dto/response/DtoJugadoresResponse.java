package cl.duoc.jugadores.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DtoJugadoresResponse {

    @Schema(description = "ID del jugador", example = "1")
    private Long id;

    @Schema(description = "Nombre del jugador", example = "John Doe")
    private String nombre;

    @Schema(description = "Nacionalidad del jugador", example = "Estadounidense")
    private String nacionalidad;

    @Schema(description = "Posición del jugador", example = "Delantero")
    private String posicion;

    @Schema(description = "Edad del jugador", example = "25")
    private Integer edad;

    @Schema(description = "URL de la foto del jugador", example = "https://example.com/foto.jpg")
    private String fotoUrl;

    @Schema(description = "ID del club al que pertenece el jugador", example = "1")
    private Long clubId;

}

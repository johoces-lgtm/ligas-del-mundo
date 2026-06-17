package cl.duoc.jugadores.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoJugadoresRequest {

    @NotNull(message = "El ID del jugador es obligatorio")
    @Schema(description = "ID del jugador", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Schema(description = "Nombre del jugador", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = "Nacionalidad del jugador", example = "Estadounidense", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String nacionalidad;

    @Schema(description = "Posición del jugador", example = "Delantero", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String posicion;

    @Schema(description = "Edad del jugador", example = "25", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer edad;
    
    @Schema(description = "URL de la foto del jugador", example = "https://example.com/foto.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String fotoUrl;

    @NotNull(message = "Debes indicar a qué club pertenece el jugador")
    @Schema(description = "ID del club al que pertenece el jugador", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long clubId;

}

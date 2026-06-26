package duoc.cl.entrenadores.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Objeto que representa la respuesta con los datos de un Entrenador")
public class DtoEntrenadorResponse {

    @Schema(description = "Identificador unico del entrenador", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del entrenador", example = "Pep Guardiola")
    private String nombre;

    @Schema(description = "Pais de origen del entrenador", example = "Española")
    private String nacionalidad;

    @Schema(description = "ID del club asignado", example = "10")
    private Long idClub;
}
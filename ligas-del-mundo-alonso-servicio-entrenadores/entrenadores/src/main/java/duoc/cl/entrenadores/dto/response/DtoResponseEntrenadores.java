package duoc.cl.entrenadores.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Objeto que representa la respuesta con los datos de un Entrenador")
public class DtoResponseEntrenadores {

    @Schema(description = "Identificador único del entrenador", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del entrenador", example = "Pep Guardiola")
    private String nombre;

    @Schema(description = "País de origen del entrenador", example = "Española")
    private String nacionalidad;

    @Schema(description = "Edad actual del entrenador", example = "53")
    private Integer edad;
}
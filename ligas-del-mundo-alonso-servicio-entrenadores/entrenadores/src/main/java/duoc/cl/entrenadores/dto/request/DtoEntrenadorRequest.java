package duoc.cl.entrenadores.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "Objeto que representa los datos necesarios para registrar o actualizar un Entrenador")
public class DtoEntrenadorRequest {

    @NotNull(message = "El ID del entrenador no puede ser nulo")
    @Schema(description = "Identificador único del entrenador", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del entrenador es obligatorio")
    @Schema(description = "Nombre completo del director técnico", example = "Pep Guardiola")
    private String nombre;

    @NotBlank(message = "La nacionalidad no puede estar vacía")
    @Schema(description = "País de origen del entrenador", example = "Española")
    private String nacionalidad;

    @NotNull(message = "El ID del club es obligatorio")
    @Schema(description = "ID del club al que está asignado", example = "10")
    private Long idClub;
}
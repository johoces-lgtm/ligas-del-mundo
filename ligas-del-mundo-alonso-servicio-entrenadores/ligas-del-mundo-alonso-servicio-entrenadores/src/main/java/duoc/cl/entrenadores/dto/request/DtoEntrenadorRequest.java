package duoc.cl.entrenadores.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "Objeto que representa los datos necesarios para registrar o actualizar un Entrenador")
public class DtoEntrenadorRequest {

    @NotNull(message = "El ID del entrenador no puede ser nulo")
    @Schema(description = "Identificador unico del entrenador", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotBlank(message = "El nombre del entrenador es obligatorio")
    @Schema(description = "Nombre completo del director tecnico", example = "Pep Guardiola", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "La nacionalidad no puede estar vacia")
    @Schema(description = "Pais de origen del entrenador", example = "Española", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nacionalidad;

    @NotNull(message = "El ID del club es obligatorio")
    @Schema(description = "ID del club al que esta asignado", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idClub;
}
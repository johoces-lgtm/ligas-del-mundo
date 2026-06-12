package duoc.cl.estadios.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "Objeto que representa los datos necesarios para registrar o actualizar un Estadio")
public class DtoEstadiosRequest {

    @NotNull(message = "El ID del estadio no puede ser nulo")
    @Schema(description = "Identificador único del estadio", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del estadio es obligatorio")
    @Schema(description = "Nombre oficial del estadio", example = "Estadio Nacional")
    private String nombre;

    @NotNull(message = "La capacidad no puede ser nula")
    @Schema(description = "Capacidad máxima de espectadores", example = "48000")
    private Integer capacidad;

    @NotNull(message = "El ID del país es obligatorio")
    @Schema(description = "Identificador del país donde se encuentra el estadio", example = "5")
    private Long idPais;
}
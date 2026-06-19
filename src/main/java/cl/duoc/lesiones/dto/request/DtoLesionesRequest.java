package cl.duoc.lesiones.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class DtoLesionesRequest {

    @NotBlank(message = "El tipo de lesión es obligatorio")
    @Schema(description = "Tipo de lesión (e.g., 'Esguince', 'Fractura')", example = "Esguince", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tipoLesion;

    @Schema(description = "Gravedad de la lesión (e.g., 'Leve', 'Moderada', 'Grave')", example = "Moderada", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String gravedad;

    @NotNull(message = "Debe indicar la fecha de inicio de la lesión")
    @Schema(description = "Fecha de inicio de la lesión", example = "2023-01-01", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate fechaInicio;

    @Schema(description = "Fecha estimada de recuperación", example = "2023-12-31", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private LocalDate fechaEstimadaRecuperacion;

    @NotNull(message = "La lesión debe estar asignada a un jugador (ID)")
    @Schema(description = "ID del jugador al que está asignada la lesión", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long jugadorId;
}
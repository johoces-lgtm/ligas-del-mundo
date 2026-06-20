package duoc.cl.clubes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoClubesRequest {

    @NotNull(message = "El ID de la API no puede estar vacío")
    @Schema(description = "ID del club", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id; 

    @NotBlank(message = "El nombre del club es obligatorio")
    @Schema(description = "Nombre oficial del club", example = "Real Madrid", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = "URL del logotipo del club", example = "https://example.com/logo.png", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String logoUrl;

    @Min(value = 1800, message = "El año de fundación debe ser válido")
    @Schema(description = "Año de fundación del equipo", example = "1902", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer anioFundacion;

    @Schema(description = "Nombre del estadio local", example = "Santiago Bernabéu", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String estadioNombre;

    @NotNull(message = "La referencia de la liga es obligatoria")
    @Schema(description = "ID de la liga a la que pertenece", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long ligaId;
}
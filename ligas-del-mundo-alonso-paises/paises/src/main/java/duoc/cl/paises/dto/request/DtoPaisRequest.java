package duoc.cl.paises.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class DtoPaisRequest {

    @NotNull(message = "El ID del pais no puede ser nulo")
    @Schema(description = "ID del pais", example = "45", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre oficial del pais", example = "Chile", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "El codigo ISO es obligatorio")
    @Schema(description = "Codigo ISO Alfa-2 o Alfa-3 del territorio", example = "CL", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoIso;

    @NotBlank(message = "La URL de la bandera no puede estar vacia")
    @Schema(description = "Enlace URL directo hacia la imagen vectorizada de la bandera", example = "https://example.com/cl.svg", requiredMode = Schema.RequiredMode.REQUIRED)
    private String urlBandera;
}
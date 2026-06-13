package duoc.cl.paises.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "Objeto que representa los datos necesarios para registrar o actualizar un País")
public class DtoPaisesRequest {

    @NotNull(message = "El ID del país no puede ser nulo")
    @Schema(description = "Identificador único del país", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del país es obligatorio")
    @Schema(description = "Nombre oficial del país", example = "Chile")
    private String nombre;

    @NotBlank(message = "El código ISO es obligatorio")
    @Schema(description = "Código ISO de 2 o 3 letras del país", example = "CL")
    private String codigoIso;

    @NotBlank(message = "El continente es obligatorio")
    @Schema(description = "Continente al que pertenece el país", example = "América del Sur")
    private String continente;
}
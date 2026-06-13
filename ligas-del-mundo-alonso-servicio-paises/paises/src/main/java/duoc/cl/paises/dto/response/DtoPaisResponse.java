package duoc.cl.paises.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Objeto que representa la respuesta con los datos de un País")
public class DtoResponsePaises {

    @Schema(description = "Identificador único del país", example = "1")
    private Long id;

    @Schema(description = "Nombre oficial del país", example = "Chile")
    private String nombre;

    @Schema(description = "Código ISO del país", example = "CL")
    private String codigoIso;

    @Schema(description = "Continente al que pertenece", example = "América del Sur")
    private String continente;
}
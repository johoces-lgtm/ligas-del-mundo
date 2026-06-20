package duoc.cl.paises.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoPaisResponse {

    @Schema(description = "ID unico asignado en la base de datos", example = "45")
    private Long id;

    @Schema(description = "Nombre del pais", example = "Chile")
    private String nombre;

    @Schema(description = "Codigo ISO representativo", example = "CL")
    private String codigoIso;

    @Schema(description = "Direccion URL de la bandera", example = "https://example.com/cl.svg")
    private String urlBandera;
}
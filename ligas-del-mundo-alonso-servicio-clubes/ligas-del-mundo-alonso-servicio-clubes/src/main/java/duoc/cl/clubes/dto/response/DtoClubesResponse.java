package duoc.cl.clubes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de respuesta que contiene los datos del Club y la información cruzada de su país")
public class DtoClubesResponse {
    @Schema(description = "Identificador único autoincremental del club", example = "1")
    private Long id;

    @Schema(description = "Nombre oficial del club", example = "Colo-Colo")
    private String nombre;

    @Schema(description = "URL del logo del club", example = "https://example.com/logos/colocolo.png")
    private String logoUrl;

    @Schema(description = "Fecha o año de fundación del club", example = "1925-04-19")
    private Integer anioFundacion;

    @Schema(description = "Nombre del estadio del club", example = "Estadio Monumental")
    private String estadioNombre;

    @Schema(description = "ID de la liga a la que pertenece el club", example = "10")
    private Long ligaId;
}
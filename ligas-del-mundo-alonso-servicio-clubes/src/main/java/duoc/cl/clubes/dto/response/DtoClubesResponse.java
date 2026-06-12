package duoc.cl.clubes.dto.response;

import lombok.Data;

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

    @Schema(description = "Ciudad principal del club", example = "Santiago")
    private String ciudad;

    @Schema(description = "Fecha de fundación", example = "1925-04-19")
    private String fundacion;

    @Schema(description = "Nombre del país asociado (obtenido desde el microservicio de Países)", example = "Chile")
    private String nombrepais;
}
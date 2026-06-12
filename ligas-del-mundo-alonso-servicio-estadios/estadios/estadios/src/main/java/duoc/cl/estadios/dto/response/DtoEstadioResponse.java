package duoc.cl.estadios.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Objeto que representa la respuesta con los datos de un Estadio")
public class DtoResponseEstadios {

    @Schema(description = "Identificador único del estadio", example = "1")
    private Long id;

    @Schema(description = "Nombre oficial del estadio", example = "Estadio Nacional")
    private String nombre;

    @Schema(description = "Capacidad máxima de espectadores", example = "48000")
    private Integer capacidad;

    @Schema(description = "Identificador del país donde se encuentra el estadio", example = "5")
    private Long idPais;
}
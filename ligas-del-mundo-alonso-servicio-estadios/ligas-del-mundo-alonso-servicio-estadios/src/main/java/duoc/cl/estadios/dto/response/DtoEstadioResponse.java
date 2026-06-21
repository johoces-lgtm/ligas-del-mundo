package duoc.cl.estadios.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoEstadioResponse {
    @Schema(description = "ID unico en persistencia", example = "12")
    private Long id;
    @Schema(description = "Nombre del complejo deportivo", example = "Estadio Nacional")
    private String nombre;
    @Schema(description = "Capacidad total", example = "45000")
    private Integer capacidad;
    @Schema(description = "ID del pais al que pertenece", example = "45")
    private Long idPais;
}
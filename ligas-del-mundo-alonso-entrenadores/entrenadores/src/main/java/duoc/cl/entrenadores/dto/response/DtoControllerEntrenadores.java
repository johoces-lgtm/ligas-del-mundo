package duoc.cl.entrenadores.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoControllerEntrenadores {
    private Long id;
    private String nombre;
    private String nacionalidad;
    private Integer edad;
}
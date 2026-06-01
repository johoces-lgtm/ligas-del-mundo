package duoc.cl.estadios.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoEstadioResponse {
    private Long id;
    private String nombre;
    private Integer capacidad;
    private Long idPais;
}
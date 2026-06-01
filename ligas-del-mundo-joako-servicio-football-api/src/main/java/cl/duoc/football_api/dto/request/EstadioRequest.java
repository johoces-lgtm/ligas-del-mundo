package cl.duoc.football_api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstadioRequest {
    private Long id;
    private String nombre;
    private Integer capacidad;
    private Long idPais;
}

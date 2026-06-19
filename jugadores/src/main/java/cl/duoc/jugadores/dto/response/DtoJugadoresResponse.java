package cl.duoc.jugadores.dto.response;

import lombok.Data;

@Data
public class DtoJugadoresResponse {
    private Long id;
    private String nombre;
    private String nacionalidad;
    private String posicion;
    private Integer edad;
    private String fotoUrl;
    private Long clubId;

}

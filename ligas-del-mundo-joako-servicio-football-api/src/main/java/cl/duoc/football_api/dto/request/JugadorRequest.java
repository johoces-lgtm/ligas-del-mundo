package cl.duoc.football_api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JugadorRequest {
    private Long id;
    private String nombre;
    private String primerNombre;
    private String apellido;
    private Integer edad;
    private String nacionalidad;
    private String fotoUrl;
    private String posicion;
    private Long clubId; 
}
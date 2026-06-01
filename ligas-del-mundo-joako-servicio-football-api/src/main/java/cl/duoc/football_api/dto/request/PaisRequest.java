package cl.duoc.football_api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaisRequest {
    private Long id;
    private String nombre;
    private String codigoIso;
    private String urlBandera;
}
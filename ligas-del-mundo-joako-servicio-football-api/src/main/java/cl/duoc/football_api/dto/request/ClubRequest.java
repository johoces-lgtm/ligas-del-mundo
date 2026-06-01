package cl.duoc.football_api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubRequest {
    private Long id;
    private String nombre;
    private String logoUrl;
    private Integer anioFundacion;
    private String estadioNombre;
    private Long ligaId;
}
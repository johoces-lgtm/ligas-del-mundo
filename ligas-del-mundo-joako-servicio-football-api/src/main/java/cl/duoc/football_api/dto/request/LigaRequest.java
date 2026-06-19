package cl.duoc.football_api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LigaRequest {
    private Long id;
    private String nombre;
    private String pais;
    private String logoUrl;
}
package cl.duoc.football_api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartidoRequest {
    private Long ligaId;
    private Long clubLocalId;
    private Long clubVisitaId;
    private Long estadioId;
    private String nombreLocal;
    private String nombreVisita;
    private Integer golesLocal;
    private Integer golesVisita;
    private String estado;
    private Integer temporada;
}
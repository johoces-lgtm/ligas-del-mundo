package cl.duoc.football_api.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ApiEjecucionResponse {
    private String mensaje;
    private String endpoint;
    private int registrosProcesados;
    private LocalDateTime fechaEjecucion;
}
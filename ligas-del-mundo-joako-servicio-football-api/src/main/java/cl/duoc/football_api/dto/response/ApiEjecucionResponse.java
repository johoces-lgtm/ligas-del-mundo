package cl.duoc.football_api.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@Schema(description = "Objeto informativo estandarizado que detalla los resultados globales de un proceso de sincronización masiva")
public class ApiEjecucionResponse {

    @Schema(description = "Mensaje informativo explicativo del resultado", example = "Extracción masiva de Clubes completada exitosamente")
    private String mensaje;

    @Schema(description = "Endpoint o proceso que se ejecutó", example = "/api/clubes/sincronizar")
    private String endpoint;

    @Schema(description = "Número total de registros procesados durante la ejecución", example = "250")
    private int registrosProcesados;

    @Schema(description = "Fecha y hora exacta en que se ejecutó el proceso", example = "2024-06-15T14:30:00")
    private LocalDateTime fechaEjecucion;
}
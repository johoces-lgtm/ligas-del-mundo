package cl.duoc.football_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO que encapsula los datos de un partido disputado para su almacenamiento histórico")
public class PartidoRequest {
    
    @Schema(description = "ID de la liga donde se disputó el encuentro", example = "39")
    private Long ligaId;
    
    @Schema(description = "ID del equipo que jugó en condición de local", example = "33")
    private Long clubLocalId;
    
    @Schema(description = "ID del equipo que jugó en condición de visita", example = "34")
    private Long clubVisitaId;
    
    @Schema(description = "ID del estadio donde se llevó a cabo el juego", example = "556")
    private Long estadioId;
    
    @Schema(description = "Nombre textual del club local", example = "Manchester United")
    private String nombreLocal;
    
    @Schema(description = "Nombre textual del club visita", example = "Newcastle")
    private String nombreVisita;
    
    @Schema(description = "Cantidad de goles anotados por el equipo local", example = "2")
    private Integer golesLocal;
    
    @Schema(description = "Cantidad de goles anotados por el equipo visita", example = "1")
    private Integer golesVisita;
    
    @Schema(description = "Estado oficial del encuentro (FT = Finished, NS = Not Started)", example = "FT")
    private String estado;
    
    @Schema(description = "Año de la temporada correspondiente", example = "2024")
    private Integer temporada;
}
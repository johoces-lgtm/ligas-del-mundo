package cl.duoc.football_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO descriptivo del rendimiento de un Club dentro de la tabla de posiciones")
public class PosicionRequest {
    
    @Schema(description = "ID del club evaluado", example = "33")
    private Long clubId;
    
    @Schema(description = "Nombre del club", example = "Manchester United")
    private String nombreClub;
    
    @Schema(description = "Puntaje total acumulado en la liga", example = "75")
    private Integer puntos;
    
    @Schema(description = "Total de partidos disputados a la fecha", example = "38")
    private Integer partidosJugados;
    
    @Schema(description = "Cantidad total de partidos ganados", example = "23")
    private Integer ganados;
    
    @Schema(description = "Cantidad total de partidos empatados", example = "6")
    private Integer empatados;
    
    @Schema(description = "Cantidad total de partidos perdidos", example = "9")
    private Integer perdidos;
    
    @Schema(description = "Goles totales anotados a favor", example = "70")
    private Integer golesFavor;
    
    @Schema(description = "Goles totales recibidos en contra", example = "43")
    private Integer golesContra;
    
    @Schema(description = "Diferencia neta de goles (Favor - Contra)", example = "27")
    private Integer diferenciaGoles;
    
    @Schema(description = "Año de la temporada competitiva", example = "2024")
    private Integer temporada;
    
    @Schema(description = "ID de la liga asociada", example = "39")
    private Long ligaId;
}
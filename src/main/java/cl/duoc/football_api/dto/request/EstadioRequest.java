package cl.duoc.football_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO para transferir datos técnicos de recintos deportivos")
public class EstadioRequest {
    @Schema(description = "ID oficial del estadio", example = "556")
    private Long id;
    @Schema(description = "Nombre del estadio", example = "Old Trafford")
    private String nombre;
    @Schema(description = "Capacidad total de espectadores", example = "74310")
    private Integer capacidad;
    @Schema(description = "ID del país al que pertenece geopolíticamente el recinto", example = "1")
    private Long idPais;
}

package cl.duoc.football_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO para registrar Directores Técnicos en el sistema local")
public class EntrenadorRequest {
    @Schema(description = "ID oficial del entrenador", example = "18")
    private Long id;
    @Schema(description = "Nombre completo del DT", example = "Erik ten Hag")
    private String nombre;
    @Schema(description = "País de origen", example = "Netherlands")
    private String nacionalidad;
    @Schema(description = "ID del club al que dirige", example = "33")
    private Long idClub;
}
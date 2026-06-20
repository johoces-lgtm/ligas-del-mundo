package cl.duoc.football_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO para dar de alta registros de Países")
public class PaisRequest {
    @Schema(description = "ID secuencial asignado de forma interna", example = "1")
    private Long id;
    @Schema(description = "Nombre del país", example = "England")
    private String nombre;
    @Schema(description = "Código internacional ISO de 2 letras", example = "GB")
    private String codigoIso;
    @Schema(description = "URL directa hacia la bandera oficial del país", example = "https://media.api-sports.io/flags/gb.svg")
    private String urlBandera;
}
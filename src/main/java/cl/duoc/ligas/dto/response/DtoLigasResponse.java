package cl.duoc.ligas.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DtoLigasResponse {

    @Schema(description = "Identificador único de la liga", example = "1")
    private Long id;

    @Schema(description = "Nombre oficial de la liga", example = "Premier League")
    private String nombre;

    @Schema(description = "País donde se desarrolla la liga", example = "Inglaterra")
    private String pais;

    @Schema(description = "URL oficial del logo de la liga", example = "https://media.api-sports.io/football/leagues/39.png")
    private String logoUrl;


}

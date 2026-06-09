package cl.duoc.ligas.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Objeto que representa los datos de entrada para registrar o actualizar una Liga")
public class DtoLigasRequest {

    @NotNull(message = "El ID oficial de la liga es obligatorio")
    @Schema(description = "ID único oficial proveniente de la API externa (API-Sports)", example = "39", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotBlank(message = "El nombre de la liga es obligatorio")
    @Schema(description = "Nombre oficial de la liga", example = "Premier League", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "El país de la liga es obligatorio")
    @Schema(description = "País donde se desarrolla la liga", example = "Inglaterra", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pais;

    @Schema(description = "URL oficial del logo de la liga", example = "https://media.api-sports.io/football/leagues/39.png")
    private String logoUrl;
}
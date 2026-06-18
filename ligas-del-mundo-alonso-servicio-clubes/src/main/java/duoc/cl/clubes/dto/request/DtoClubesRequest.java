package duoc.cl.clubes.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

@Schema(description = "Objeto de transferencia para registrar o actualizar un Club de fútbol")
public class DtoClubesRequest {
    
    @NotNull(message = "El ID del club no puede ser nulo")
    @Min(value = 1, message = "El ID del club debe ser mayor o igual a 1")
    @Schema(description = "Identificador único del club (autoincremental)", example = "1")
    private Long id;

    @NotBlank(message = "El nombre del club no puede estar vacio")
    @Schema(description = "Nombre oficial del club", example = "Colo-Colo")
    private String nombre;

    @Schema(description = "URL del logo del club", example = "https://example.com/logos/colocolo.png")
    private String logoUrl;

    @NotBlank(message = "La fecha de fundacion no puede estar vacia")
    @Schema(description = "Fecha o año de fundación del club", example = "1925-04-19")
    private Integer anioFundacion;

    @Schema(description = "Nombre del estadio del club", example = "Estadio Monumental")
    private String estadioNombre;

    @NotNull(message = "El ID de la liga no puede ser nulo")
    @Min(value = 1, message = "El ID de la liga debe ser mayor o igual a 1")
    @Schema(description = "ID de la liga a la que pertenece el club", example = "10")
    private Long ligaId;
}


package duoc.cl.clubes.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Objeto de transferencia para registrar o actualizar un Club de fútbol")
public class DtoClubesRequest {

    @NotBlank(message = "El nombre del club no puede estar vacio")
    @Schema(description = "Nombre oficial del club", example = "Colo-Colo")
    private String nombre;

    @NotBlank(message = "La ciudad no puede estar vacia")
    @Schema(description = "Ciudad principal donde reside el club", example = "Santiago")
    private String ciudad;

    @NotBlank(message = "La fecha de fundacion no puede estar vacia")
    @Schema(description = "Fecha o año de fundación del club", example = "1925-04-19")
    private String fundacion;

    @NotNull(message = "El id del pais no puede ser nulo")
    @Min(value = 1, message = "El id del pais debe ser mayor o igual a 1")
    @Schema(description = "ID único del país al que pertenece el club", example = "1")
    private Long idpais;
}


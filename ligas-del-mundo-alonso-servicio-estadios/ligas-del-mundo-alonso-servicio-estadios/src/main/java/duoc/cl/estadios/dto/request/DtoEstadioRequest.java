package duoc.cl.estadios.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class DtoEstadioRequest {

    @NotNull(message = "El ID del estadio es requerido")
    @Schema(description = "Identificador unico del estadio", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @NotBlank(message = "El nombre del estadio no puede estar vacio")
    @Schema(description = "Nombre comercial u oficial del recinto deportivo", example = "Estadio Nacional", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Schema(description = "Aforo maximo permitido de espectadores", example = "45000")
    private Integer capacidad;

    @NotNull(message = "El ID del pais es obligatorio")
    @Schema(description = "ID del pais (Asociado mediante WebClient a servicio-paises)", example = "45", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idPais;
}
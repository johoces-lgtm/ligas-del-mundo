package duoc.cl.estadios.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class DtoEstadioRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String nombre;
    private Integer capacidad;
    @NotNull
    private Long idPais;
}
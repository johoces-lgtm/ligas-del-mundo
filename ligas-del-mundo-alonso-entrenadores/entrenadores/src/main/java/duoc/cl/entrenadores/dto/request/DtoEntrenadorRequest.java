package duoc.cl.entrenadores.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class DtoEntrenadorRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String nacionalidad;
    @NotNull
    private Long idClub;
}
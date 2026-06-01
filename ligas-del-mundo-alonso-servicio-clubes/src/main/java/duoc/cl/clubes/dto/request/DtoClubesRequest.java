package duoc.cl.clubes.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoClubesRequest {

    @NotNull(message = "El ID de la API no puede estar vacío")
    private Long id; 

    @NotBlank(message = "El nombre del club es obligatorio")
    private String nombre;

    private String logoUrl;

    @Min(value = 1800, message = "El año de fundación debe ser válido")
    private Integer anioFundacion;

    private String estadioNombre;

    @NotNull(message = "La referencia de la liga es obligatoria")
    private Long ligaId;
}
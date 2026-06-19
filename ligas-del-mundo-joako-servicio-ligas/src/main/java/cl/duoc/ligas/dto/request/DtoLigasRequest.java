package cl.duoc.ligas.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DtoLigasRequest {

    @NotBlank(message = "El nombre de la liga es obligatorio")
    private String nombre;

    @NotBlank(message = "El país de la liga es obligatorio")
    private String pais;

    private String logoUrl;

}

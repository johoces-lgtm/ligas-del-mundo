package duoc.cl.paises.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class DtoPaisRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String codigoIso;
    @NotBlank
    private String urlBandera;
}
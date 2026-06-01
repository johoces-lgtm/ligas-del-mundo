package duoc.cl.paises.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoPaisResponse {
    private Long id;
    private String nombre;
    private String codigoIso;
    private String urlBandera;
}
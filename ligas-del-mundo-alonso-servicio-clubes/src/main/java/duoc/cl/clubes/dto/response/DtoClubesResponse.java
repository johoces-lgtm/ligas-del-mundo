package duoc.cl.clubes.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DtoClubesResponse {
    @Schema(description = "ID único del club", example = "1")
    private Long id;
    
    @Schema(description = "Nombre del club", example = "Real Madrid")
    private String nombre;
    
    @Schema(description = "URL del logo", example = "https://example.com/logo.png")
    private String logoUrl;
    
    @Schema(description = "Año de fundación", example = "1902")
    private Integer anioFundacion;
    
    @Schema(description = "Estadio local", example = "Santiago Bernabéu")
    private String estadioNombre;
    
    @Schema(description = "ID de la liga asociada", example = "2")
    private Long ligaId;
}
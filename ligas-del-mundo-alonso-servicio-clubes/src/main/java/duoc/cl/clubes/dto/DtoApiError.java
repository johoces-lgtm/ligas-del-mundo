package duoc.cl.clubes.dto;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DtoApiError {
    
    @Schema(description = "Fecha y hora del error", example = "2026-06-20")
    private LocalDate timestamp;
    
    @Schema(description = "Código de estado HTTP", example = "404")
    private Integer status;
    
    @Schema(description = "Tipo de error HTTP", example = "Not Found")
    private String error;
    
    @Schema(description = "Mensaje explicativo del error", example = "Club no encontrado con ID: 99")
    private String message; 
    
    @Schema(description = "Ruta del endpoint accedido", example = "/api/clubes/99")
    private String path;
    
    @Schema(description = "Nombre de la excepción lanzada", example = "ResourceNotFoundException")
    private String claseException;
}
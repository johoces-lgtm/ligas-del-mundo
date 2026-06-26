package duoc.cl.estadios.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class DtoApiError {
    @Schema(description = "Fecha exacta del suceso", example = "2026-06-20")
    private LocalDate timestamp;
    @Schema(description = "Estado HTTP", example = "404")
    private Integer status;
    @Schema(description = "Semantica literal del error", example = "Not Found")
    private String error;
    @Schema(description = "Mensaje descriptivo", example = "El pais referenciado no existe.")
    private String message;
    @Schema(description = "Context Path del recurso", example = "/api/estadios")
    private String path;
    @Schema(description = "Excepcion capturada", example = "ResourceNotFoundException")
    private String claseException;
}
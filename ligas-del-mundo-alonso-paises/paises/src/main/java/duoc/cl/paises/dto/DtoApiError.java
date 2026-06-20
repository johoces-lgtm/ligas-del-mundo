package duoc.cl.paises.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class DtoApiError {
    @Schema(description = "Fecha de generacion del evento", example = "2026-06-20")
    private LocalDate timestamp;

    @Schema(description = "Status Code HTTP", example = "404")
    private Integer status;

    @Schema(description = "Definicion semantica del error HTTP", example = "Not Found")
    private String error;

    @Schema(description = "Mensaje explicativo de la excepcion", example = "Pais no encontrado")
    private String message;

    @Schema(description = "Ruta web solicitada", example = "/api/paises/999")
    private String path;

    @Schema(description = "Clase java que provoco la excepcion", example = "ResourceNotFoundException")
    private String claseException;
}
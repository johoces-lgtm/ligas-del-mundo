package cl.duoc.football_api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class DtoApiError {
    @Schema(description = "Fecha y hora del error", example = "2024-06-01T12:00:00")
    private LocalDate timestamp;

    @Schema(description = "Código de estado HTTP", example = "400")
    private Integer status;

    @Schema(description = "Tipo de error", example = "Bad Request")
    private String error;

    @Schema(description = "Mensaje detallado del error", example = "El campo 'nombre' no puede estar vacío")
    private String message;

    @Schema(description = "Ruta del endpoint que causó el error", example = "/api/jugadores")
    private String path;
    
    @Schema(description = "Clase de la excepción", example = "IllegalArgumentException")
    private String claseException;

}
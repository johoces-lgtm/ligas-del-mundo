package cl.duoc.lesiones.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
@Schema(description = "Objeto de respuesta que presenta el registro médico de la lesión procesada")
public class DtoLesionesResponse {
    @Schema(description = "ID único e incremental autogenerado por la Base de Datos", example = "1")
    private Long id;

    @Schema(description = "Diagnóstico de la lesión", example = "Rotura de Ligamento Cruzado Anterior")
    private String tipoLesion;

    @Schema(description = "Gravedad clínica asignada", example = "Grave o Crítica")
    private String gravedad;

    @Schema(description = "Fecha de inicio del periodo inactivo", example = "2026-06-01")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha estimada de retorno a las canchas", example = "2026-12-01")
    private LocalDate fechaEstimadaRecuperacion;
    
    @Schema(description = "ID del futbolista asociado", example = "10")
    private Long jugadorId;
}
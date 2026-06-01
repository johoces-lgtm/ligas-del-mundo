package cl.duoc.lesiones.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DtoLesionesResponse {
    private Long id;
    private String tipoLesion;
    private String gravedad;
    private LocalDate fechaInicio;
    private LocalDate fechaEstimadaRecuperacion;
    private Long jugadorId;
}
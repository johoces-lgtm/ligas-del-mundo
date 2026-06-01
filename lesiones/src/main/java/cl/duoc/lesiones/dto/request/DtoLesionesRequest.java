package cl.duoc.lesiones.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class DtoLesionesRequest {

    @NotBlank(message = "El tipo de lesión es obligatorio")
    private String tipoLesion;

    private String gravedad;

    @NotNull(message = "Debe indicar la fecha de inicio de la lesión")
    private LocalDate fechaInicio;

    private LocalDate fechaEstimadaRecuperacion;

    @NotNull(message = "La lesión debe estar asignada a un jugador (ID)")
    private Long jugadorId;
}
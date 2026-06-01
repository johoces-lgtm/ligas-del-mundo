package cl.duoc.lesiones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "lesiones")
public class LesionesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de lesión es obligatorio")
    private String tipoLesion; 

    private String gravedad;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaEstimadaRecuperacion;

    @NotNull(message = "La lesión debe estar asociada a un jugador")
    @Column(name = "jugador_id")
    private Long jugadorId; 
}
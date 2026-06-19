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
    @Column(name = "id", nullable = false, length = 10)
    private Long id;

    @NotBlank(message = "El tipo de lesión es obligatorio")
    @Column(name = "tipo_lesion", nullable = false, length = 100)
    private String tipoLesion; 

    @Column(name = "gravedad", nullable = true, length = 100)
    private String gravedad;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_estimada_recuperacion", nullable = true)
    private LocalDate fechaEstimadaRecuperacion;

    @NotNull(message = "La lesión debe estar asociada a un jugador")
    @Column(name = "jugador_id", nullable = false)
    private Long jugadorId; 
}
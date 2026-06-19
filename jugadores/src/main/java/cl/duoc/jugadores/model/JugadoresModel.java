package cl.duoc.jugadores.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "jugadores")
public class JugadoresModel {
    
    @Id
    private Long id;

    @NotBlank(message = "El nombre del jugador es obligatorio")
    @Column(nullable = false)
    private String nombre;
    private String nacionalidad;
    private String posicion;
    private Integer edad;
    private String fotoUrl;

    @NotNull(message = "El jugador debe pertenecer a un club")
    @Column(name = "club_id", nullable = false)
    private Long clubId;

}

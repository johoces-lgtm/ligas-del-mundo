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
    @Column(name = "id", nullable = false, length = 10)
    private Long id;

    @NotBlank(message = "El nombre del jugador es obligatorio")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "nacionalidad", nullable = true, length = 100)
    private String nacionalidad;

    @Column(name = "posicion", nullable = true, length = 100)
    private String posicion;

    @Column(name = "edad", nullable = true)
    private Integer edad;

    @Column(name = "foto_url", nullable = true)
    private String fotoUrl;

    @NotNull(message = "El jugador debe pertenecer a un club")
    @Column(name = "club_id", nullable = false)
    private Long clubId;

}

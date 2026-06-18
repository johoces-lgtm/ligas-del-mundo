package duoc.cl.entrenadores.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "entrenadores")
public class EntrenadorModel {
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "nacionalidad", nullable = false, length = 100)
    private String nacionalidad;
    
    @Column(name = "id_club", nullable = false)
    private Long idClub;
}
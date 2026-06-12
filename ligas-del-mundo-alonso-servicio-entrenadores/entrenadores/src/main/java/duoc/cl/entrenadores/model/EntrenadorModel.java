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
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String nacionalidad;
    
    @Column(nullable = false)
    private Long idClub;
}
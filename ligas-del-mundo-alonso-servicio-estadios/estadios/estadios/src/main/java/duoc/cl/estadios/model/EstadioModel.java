package duoc.cl.estadios.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "estadios")
public class EstadioModel {
    @Id
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private Integer capacidad;
    
    @Column(nullable = false)
    private Long idPais;
}
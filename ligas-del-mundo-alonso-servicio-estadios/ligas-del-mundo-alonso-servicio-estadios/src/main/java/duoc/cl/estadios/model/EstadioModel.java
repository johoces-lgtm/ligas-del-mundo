package duoc.cl.estadios.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "estadios")
public class EstadioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 10)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;
    
    @Column(name = "id_pais", nullable = false)
    private Long idPais;
}
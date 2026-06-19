package duoc.cl.paises.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "paises")
public class PaisModel {
    @Id
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false, length = 10)
    private String codigoIso;
    
    @Column(nullable = false)
    private String urlBandera;
}
package duoc.cl.paises.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "paises")
public class PaisModel {
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "codigo_iso", nullable = false, length = 10)
    private String codigoIso;
    
    @Column(name = "url_bandera", nullable = false)
    private String urlBandera;
}
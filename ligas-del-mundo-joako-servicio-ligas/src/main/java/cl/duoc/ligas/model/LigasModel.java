package cl.duoc.ligas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ligas")
public class LigasModel {

    @Id
    @Column(name = "id", nullable = false, length = 10)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "pais", nullable = false, length = 100)
    private String pais;

    @Column(name = "logo_url", nullable = true)
    private String logoUrl;

}

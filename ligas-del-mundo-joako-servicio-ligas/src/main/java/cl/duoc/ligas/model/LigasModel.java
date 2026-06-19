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
    private Long id;

    @Column(nullable = false, unique = true )
    private String nombre;

    @Column(nullable = false)
    private String pais;

    private String logoUrl;

}

package duoc.cl.clubes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clubes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClubesModel {

    @Id
    private Long id; 

    @Column(nullable = false)
    private String nombre;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "anio_fundacion")
    private Integer anioFundacion;

    @Column(name = "estadio_nombre")
    private String estadioNombre;

    @Column(name = "liga_id", nullable = false)
    private Long ligaId;
}
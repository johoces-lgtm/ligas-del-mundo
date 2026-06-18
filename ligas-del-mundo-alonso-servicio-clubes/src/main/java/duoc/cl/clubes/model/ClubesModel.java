package duoc.cl.clubes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "id", nullable = false)
    private Long id; 

    @NotBlank(message = "El nombre del club es obligatorio")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @Column(name = "anio_fundacion", nullable = false, length = 4)
    private Integer anioFundacion;

    @Column(name = "estadio_nombre", length = 100)
    private String estadioNombre;

    @NotNull(message = "El ID de la liga es obligatorio")
    @Column(name = "liga_id", nullable = false, length = 10)
    private Long ligaId;
}
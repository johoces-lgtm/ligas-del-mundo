package cl.duoc.football_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "api_guardados")
public class ApiModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "endpoint_consultado", nullable = false)
    private String endpointConsultado;

    @Column(name = "fecha_ejecucion", nullable = false)
    private LocalDateTime fechaEjecucion;

    @Column(nullable = false)
    private String estado;

    @Column(columnDefinition = "TEXT")
    private String observacion;
    
    @Column(name = "registros_procesados")
    private Integer registrosProcesados;
}
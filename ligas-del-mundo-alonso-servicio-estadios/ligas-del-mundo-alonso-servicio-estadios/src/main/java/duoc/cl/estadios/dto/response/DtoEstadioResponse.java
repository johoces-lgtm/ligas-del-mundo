package duoc.cl.estadios.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
<<<<<<< HEAD
import lombok.AllArgsConstructor;
=======
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
<<<<<<< HEAD
@NoArgsConstructor 
@AllArgsConstructor 
@Schema(description = "Objeto que representa la respuesta con los datos de un Estadio")
=======
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
public class DtoEstadioResponse {
    @Schema(description = "ID unico en persistencia", example = "12")
    private Long id;
    @Schema(description = "Nombre del complejo deportivo", example = "Estadio Nacional")
    private String nombre;
    @Schema(description = "Capacidad total", example = "45000")
    private Integer capacidad;
    @Schema(description = "ID del pais al que pertenece", example = "45")
    private Long idPais;
}
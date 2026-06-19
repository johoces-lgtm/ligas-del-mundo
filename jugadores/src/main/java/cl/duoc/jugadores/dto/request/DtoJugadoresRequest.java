package cl.duoc.jugadores.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DtoJugadoresRequest {

    @NotNull(message = "El ID del jugador es obligatorio")
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String nacionalidad;
    private String posicion;
    private Integer edad;
    private String fotoUrl;

    @NotNull(message = "Debes indicar a qué club pertenece el jugador")
    private Long clubId;

}

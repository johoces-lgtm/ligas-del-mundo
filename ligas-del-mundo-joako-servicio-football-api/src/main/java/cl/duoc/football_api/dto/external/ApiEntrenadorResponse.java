package cl.duoc.football_api.dto.external;

import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ApiEntrenadorResponse {
    
    private List<DatosEntrenador> response;

    @Data
    public static class DatosEntrenador {
        private Long id;
        @JsonProperty("name")
        private String nombre;
        
        @JsonProperty("nationality")
        private String nacionalidad;
        
        @JsonProperty("team")
        private EquipoEntrenador equipo;
    }

    @Data
    public static class EquipoEntrenador {
        private Long id;
    }
}
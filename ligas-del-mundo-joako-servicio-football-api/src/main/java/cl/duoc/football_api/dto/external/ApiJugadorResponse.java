package cl.duoc.football_api.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ApiJugadorResponse {
    
    private List<EntradaJugador> response;

    private Paging paging; 

    private Object errors;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Paging {
        private Integer current;
        private Integer total;
    }

    @Data
    public static class EntradaJugador {
        @JsonProperty("player")
        private DatosJugador jugador;

        @JsonProperty("statistics")
        private List<DatosEstadisticas> estadisticas;
    }

    @Data
    public static class DatosJugador {
        private Long id; 
        
        @JsonProperty("name")
        private String nombre;
        
        @JsonProperty("firstname")
        private String primerNombre;
        
        @JsonProperty("lastname")
        private String apellido;
        
        @JsonProperty("age")
        private Integer edad;
        
        @JsonProperty("nationality")
        private String nacionalidad;
        
        @JsonProperty("photo")
        private String fotoUrl;
    }

    @Data
    public static class DatosEstadisticas {
        @JsonProperty("games")
        private DatosJuegos juegos;
    }

    @Data
    public static class DatosJuegos {
        @JsonProperty("position")
        private String posicion;
    }
}
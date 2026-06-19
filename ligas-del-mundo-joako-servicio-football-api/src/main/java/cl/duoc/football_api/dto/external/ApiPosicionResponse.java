package cl.duoc.football_api.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPosicionResponse {
    
    private List<DatosLigaPosicion> response;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DatosLigaPosicion {
        @JsonProperty("league")
        private LigaPosiciones liga;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LigaPosiciones {
        private Long id;
        
        @JsonProperty("name")
        private String nombre;
        
        @JsonProperty("season")
        private Integer temporada;

        @JsonProperty("standings")
        private List<List<DetallePosicion>> tabla;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DetallePosicion {
        @JsonProperty("rank")
        private Integer posicion;

        @JsonProperty("team")
        private EquipoPosicion equipo;

        @JsonProperty("points")
        private Integer puntos;

        @JsonProperty("goalsDiff")
        private Integer diferenciaGoles;

        @JsonProperty("all")
        private EstadisticasPosicion estadisticas;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EquipoPosicion {
        private Long id;
        
        @JsonProperty("name")
        private String nombre;
        
        private String logo;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EstadisticasPosicion {
        @JsonProperty("played")
        private Integer partidosJugados;

        @JsonProperty("win")
        private Integer ganados;

        @JsonProperty("draw")
        private Integer empatados;

        @JsonProperty("lose")
        private Integer perdidos;

        @JsonProperty("goals")
        private GolesPosicion goles;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GolesPosicion {
        @JsonProperty("for")
        private Integer aFavor;

        @JsonProperty("against")
        private Integer enContra;
    }
}
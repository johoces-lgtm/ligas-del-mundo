package cl.duoc.football_api.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiPartidoResponse {

    private List<DatosPartido> response;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DatosPartido {
        @JsonProperty("fixture")
        private FixtureData fixture;

        @JsonProperty("league")
        private LigaPartido liga;

        @JsonProperty("teams")
        private EquiposPartido equipos;

        @JsonProperty("goals")
        private GolesPartido goles;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FixtureData {
        private Long id;
        
        @JsonProperty("venue")
        private EstadioPartido estadio;
        
        @JsonProperty("status")
        private EstadoPartido estado;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EstadioPartido {
        private Long id;
        
        @JsonProperty("name")
        private String nombre;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EstadoPartido {
        @JsonProperty("short")
        private String corto;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LigaPartido {
        private Long id;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EquiposPartido {
        @JsonProperty("home")
        private EquipoDetalle local;

        @JsonProperty("away")
        private EquipoDetalle visita;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EquipoDetalle {
        private Long id;
        
        @JsonProperty("name")
        private String nombre;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GolesPartido {
        @JsonProperty("home")
        private Integer local;

        @JsonProperty("away")
        private Integer visita;
    }
}
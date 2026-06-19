package cl.duoc.football_api.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ApiClubResponse {

    private List<DatosResponse> response;

    @Data
    public static class DatosResponse
     {
        @JsonProperty("team")
        private DatosEquipo equipo;
        
        @JsonProperty("venue")
        private DatosEstadio estadio;
    }

    @Data
    public static class DatosEquipo {
        private Long id;
        
        @JsonProperty("name")
        private String nombre;
        
        @JsonProperty("logo")
        private String logoUrl;

        @JsonProperty("founded")
        private Integer anioFundacion;
    }

    @Data
    public static class DatosEstadio {
        private Long id;
        
        @JsonProperty("name")
        private String nombre;
        
        @JsonProperty("city")
        private String ciudad;
        
        @JsonProperty("capacity")
        private Integer capacidad;

    }
}
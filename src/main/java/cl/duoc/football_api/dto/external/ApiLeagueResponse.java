package cl.duoc.football_api.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ApiLeagueResponse {
    
    private List<DatosResponse> response;

    @Data
    public static class DatosResponse{
        @JsonProperty("league")
        private DatosLiga liga;
        
        @JsonProperty("country")
        private DatosPais pais;
    }

    @Data
    public static class DatosLiga {
        private Long id;
        
        @JsonProperty("name")
        private String nombre;
        
        @JsonProperty("type")
        private String tipo;
        
        @JsonProperty("logo")
        private String logoUrl;
    }

    @Data
    public static class DatosPais {
        @JsonProperty("name")
        private String nombre;
        
        @JsonProperty("code")
        private String codigoIso;

        @JsonProperty("flag")
        private String urlBandera;
    }
}
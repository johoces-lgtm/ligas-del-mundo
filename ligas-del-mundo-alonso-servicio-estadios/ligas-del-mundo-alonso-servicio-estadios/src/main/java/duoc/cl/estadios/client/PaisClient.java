package duoc.cl.estadios.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class PaisClient {
    private final WebClient clienteWeb;

    public PaisClient(WebClient.Builder webClientBuilder, 
                      @Value("${paises.api.url:http://localhost:8085}") String paisesUrl) {
        this.clienteWeb = webClientBuilder.baseUrl(paisesUrl).build();
    }

    public boolean validarPais(Long idPais) {
        try {
            clienteWeb.get()
                    .uri("/api/paises/{id}", idPais)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (WebClientResponseException.NotFound ex) {
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public void pingPaises() {
        try {
            clienteWeb.options()
                    .uri("/api/paises")
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e) {
        }
    }
}
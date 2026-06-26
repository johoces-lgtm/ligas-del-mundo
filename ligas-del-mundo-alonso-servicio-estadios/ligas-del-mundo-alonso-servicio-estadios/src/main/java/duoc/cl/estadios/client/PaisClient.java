package duoc.cl.estadios.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class PaisClient {
    private final WebClient clienteWeb;

<<<<<<< HEAD
    public PaisClient(WebClient.Builder webClientBuilder, 
                      @Value("${paises.api.url:http://localhost:8085}") String paisesUrl) {
        this.clienteWeb = webClientBuilder.baseUrl(paisesUrl).build();
=======
    public PaisClient(WebClient.Builder constructorWebClient, @Value("${paises.url:http://localhost:8085}") String urlPaises) {
        this.clienteWeb = constructorWebClient.baseUrl(urlPaises).build();
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
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
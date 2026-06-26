package duoc.cl.estadios.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PaisClient {
    private final WebClient clienteWeb;

    public PaisClient(WebClient.Builder webClientBuilder, 
                      @Value("${paises.api.url:http://localhost:8085}") String paisesUrl) {
        this.clienteWeb = webClientBuilder.baseUrl(paisesUrl).build();
    }

    public Mono<Boolean> validarPais(Long idPais) {
        return clienteWeb.get()
                .uri("/api/paises/{id}", idPais)
                .retrieve()
                .toBodilessEntity()
                .map(respuesta -> true)
                .onErrorReturn(false);
    }
}
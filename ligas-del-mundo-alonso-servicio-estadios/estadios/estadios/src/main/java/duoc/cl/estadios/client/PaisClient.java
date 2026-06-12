package duoc.cl.estadios.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PaisClient {
    private final WebClient clienteWeb;

    public PaisClient(WebClient.Builder constructorWebClient, @Value("${paises.url:http://localhost:8088}") String urlPaises) {
        this.clienteWeb = constructorWebClient.baseUrl(urlPaises).build();
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
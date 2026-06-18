package duoc.cl.paises.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PaisClient {
    private final WebClient webClient;

    public PaisClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8085").build();
    }

    public Mono<Boolean> validarPais(Long idPais) {
        log.info("Validando país con ID: {}", idPais);
        return webClient.get()
                .uri("/api/paises/{id}", idPais)
                .retrieve()
                .toBodilessEntity()
                .map(response -> true)
                .onErrorReturn(false);
    }
}
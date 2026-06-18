package duoc.cl.entrenadores.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class EntrenadorClient {
    private final WebClient webClient;

    public EntrenadorClient(WebClient.Builder webClientBuilder) {
        // Puerto 8087 según configuracion_propiedades_7.txt
        this.webClient = webClientBuilder.baseUrl("http://localhost:8087").build();
    }

    public Mono<Boolean> validarEntrenador(Long id) {
        log.info("Validando entrenador con ID: {}", id);
        return webClient.get()
                .uri("/api/entrenadores/{id}", id)
                .retrieve()
                .toBodilessEntity()
                .map(response -> true)
                .onErrorReturn(false);
    }
}
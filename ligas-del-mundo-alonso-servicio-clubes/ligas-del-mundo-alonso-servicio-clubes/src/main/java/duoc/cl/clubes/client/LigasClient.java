package duoc.cl.clubes.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LigasClient {

    private final WebClient webClient;

    public LigasClient(WebClient.Builder webClientBuilder, 
                       @Value("${ligas.api.url:http://localhost:8081}") String ligasUrl) {
        this.webClient = webClientBuilder.baseUrl(ligasUrl).build();
    }

    public Mono<Boolean> validarLiga(Long ligaId) {
        log.info("Consultando al servicio-ligas si existe la liga ID: {}", ligaId);
        return webClient.get()
                .uri("/api/ligas/{id}", ligaId)
                .retrieve()
                .toBodilessEntity()
                .map(response -> true)
                .onErrorResume(WebClientResponseException.NotFound.class, ex -> {
                    log.warn("La liga {} NO existe (Error 404)", ligaId);
                    return Mono.just(false); 
                })
                .onErrorResume(Exception.class, ex -> {
                    log.error("Servicio de ligas inactivo o fallando: {}", ex.getMessage());
                    return Mono.error(new RuntimeException("El servicio de ligas no está disponible."));
                });
    }
}
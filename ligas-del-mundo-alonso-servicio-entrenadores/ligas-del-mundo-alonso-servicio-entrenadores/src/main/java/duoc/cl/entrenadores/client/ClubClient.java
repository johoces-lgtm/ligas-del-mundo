package duoc.cl.entrenadores.client; 

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
public class ClubClient {

    private final WebClient webClient;

    public ClubClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build();
    }

    public boolean validarClub(Long clubId) {
        log.info("Validando club para entrenador, ID: {}", clubId);
        try {
            webClient.get()
                    .uri("/api/clubes/{id}", clubId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (WebClientResponseException.NotFound ex) {
            log.warn("El club {} no existe (404)", clubId);
            return false;
        } catch (Exception ex) {
            log.error("Error validando club: {}", ex.getMessage());
            throw new RuntimeException("Servicio de clubes no disponible");
        }
    }
}
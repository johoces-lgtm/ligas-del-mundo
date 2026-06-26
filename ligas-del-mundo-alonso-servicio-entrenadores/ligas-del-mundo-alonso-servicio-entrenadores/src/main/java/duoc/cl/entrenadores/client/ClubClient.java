package duoc.cl.entrenadores.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
public class ClubClient {

    private final WebClient webClient;

    public ClubClient(WebClient.Builder webClientBuilder, @Value("${clubes.url:http://localhost:8082}") String urlClubes) {
        this.webClient = webClientBuilder.baseUrl(urlClubes).build();
    }

    public boolean validarClub(Long clubId) {
        log.info("Validando club para entrenador, ID externo: {}", clubId);
        try {
            webClient.get()
                    .uri("/api/clubes/{id}", clubId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (WebClientResponseException.NotFound ex) {
            log.warn("El club con ID {} no existe en el sistema (404)", clubId);
            return false;
        } catch (Exception ex) {
            log.error("Error critico validando club: {}", ex.getMessage());
            return false;
        }
    }
}
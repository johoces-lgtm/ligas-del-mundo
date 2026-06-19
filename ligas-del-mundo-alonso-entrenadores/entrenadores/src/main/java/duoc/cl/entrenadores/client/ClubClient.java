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
        try {
            webClient.get().uri("/api/clubes/{id}", clubId).retrieve().toBodilessEntity().block();
            return true;
        } catch (WebClientResponseException.NotFound ex) {
            return false;
        }
    }

    public void pingClubes() {
        webClient.options().uri("/api/clubes").retrieve().toBodilessEntity().block();
    }
}
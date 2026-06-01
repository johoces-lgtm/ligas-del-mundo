package duoc.cl.clubes.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
public class LigasClient {

    private final WebClient webClient;

    public LigasClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    public boolean validarLiga(Long ligaId) {
        try {
            webClient.get().uri("/api/ligas/{id}", ligaId).retrieve().toBodilessEntity().block();
            return true;
        } catch (WebClientResponseException.NotFound ex) {
            return false;
        }
    }

    public void pingLigas() {
        webClient.options().uri("/api/ligas").retrieve().toBodilessEntity().block();
    }
}
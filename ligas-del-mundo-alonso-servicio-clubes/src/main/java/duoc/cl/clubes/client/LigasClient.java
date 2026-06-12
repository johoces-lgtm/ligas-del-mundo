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
        log.info("Consultando al servicio-ligas si existe la liga ID: {}", ligaId);
        try {
            webClient.get()
                    .uri("/api/ligas/{id}", ligaId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            return true;
        } catch (WebClientResponseException.NotFound ex) {
            log.warn("La liga {} NO existe (Error 404)", ligaId);
            return false;
        } catch (Exception ex) {
            log.error("Servicio de ligas inactivo: {}", ex.getMessage());
            throw new RuntimeException("El servicio de ligas no está disponible para validar.");
        }
    }
}
package cl.duoc.jugadores.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.time.Duration;

@Slf4j
@Component
public class ClubesClient {

    private final WebClient webClient;

    public ClubesClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build();
    }

    public boolean validarClub(Long clubId) {
        log.info("Llamando al servicio-clubes para validar ID: {}", clubId);
        try {
            webClient.get()
                    .uri("/api/clubes/{id}", clubId)
                    .retrieve()
                    .toBodilessEntity() 
                    .timeout(Duration.ofSeconds(3))
                    .block(); 
            
            return true; 

        } catch (WebClientResponseException.NotFound ex) {
            log.warn("El servicio-clubes respondió que el club {} NO existe (404)", clubId);
            return false;
            
        } catch (Exception ex) {
            // Si el servicio externo se queda colgado, entra aquí tras 3 segundos.
            log.error("Fallo crítico de comunicación o Timeout con servicio-clubes para el ID {}. Error: {}", clubId, ex.getMessage());
            throw new RuntimeException("El servicio de clubes no se encuentra disponible temporalmente. Intente más tarde.");
        }
    }
}
package cl.duoc.lesiones.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Component
public class JugadoresClient {

    private final WebClient webClient;

    @Autowired
    public JugadoresClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8083").build();
    }

    public boolean validarJugador(Long jugadorId) {
        try {
            webClient.get().uri("/api/jugadores/{id}", jugadorId).retrieve().toBodilessEntity().block();
            return true;
        } catch (WebClientResponseException.NotFound ex) {
            return false;
        }
    }

    public void pingJugadores() {
        webClient.options().uri("/api/jugadores").retrieve().toBodilessEntity().block();
    }
}
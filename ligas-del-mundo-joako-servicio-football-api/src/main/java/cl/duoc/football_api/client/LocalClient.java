package cl.duoc.football_api.client;

import cl.duoc.football_api.dto.request.ClubRequest;
import cl.duoc.football_api.dto.request.EntrenadorRequest;
import cl.duoc.football_api.dto.request.EstadioRequest;
import cl.duoc.football_api.dto.request.LigaRequest;
import cl.duoc.football_api.dto.request.PaisRequest;
import cl.duoc.football_api.dto.request.PartidoRequest;
import cl.duoc.football_api.dto.request.PosicionRequest;
import cl.duoc.football_api.dto.request.JugadorRequest;
import cl.duoc.football_api.dto.response.ClubResponse; 
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class LocalClient {

    private final WebClient.Builder webClientBuilder;

    public LocalClient(@Qualifier("internalWebClient") WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public void enviarLigaALocal(LigaRequest request) {
        log.info("Enviando liga '{}' al microservicio de Ligas (8081)...", request.getNombre());
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8081/api/ligas")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void enviarClubALocal(ClubRequest pedido) {
        log.info("Enviando club '{}' al microservicio de Clubes (8082)...", pedido.getNombre());
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8082/api/clubes")
                .bodyValue(pedido)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public List<ClubResponse> obtenerTodosLosClubes() {
        log.info("Obteniendo todos los clubes locales para extraer sus jugadores...");
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/clubes") 
                .retrieve()
                .bodyToFlux(ClubResponse.class)
                .collectList()
                .block();
    }

    public void enviarJugadorALocal(JugadorRequest pedido) {
        log.info("Enviando jugador '{}' al microservicio de Jugadores (8083)...", pedido.getNombre());
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/jugadores")
                .bodyValue(pedido)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void enviarPartidoALocal(PartidoRequest pedido) {
        log.info("Enviando partido '{} vs {}' al microservicio de Partidos (8088)...", pedido.getNombreLocal(), pedido.getNombreVisita());
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8088/api/partidos")
                .bodyValue(pedido)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void enviarPosicionALocal(PosicionRequest pedido) {
        log.info("Enviando posición de '{}' al microservicio de Posiciones (8089)...", pedido.getNombreClub());
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8089/api/posiciones") 
                .bodyValue(pedido)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void enviarPaisALocal(PaisRequest request) {
        log.info("Enviando País ID {} al microservicio local", request.getId());
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8085/api/paises")
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void enviarEstadioALocal(EstadioRequest request) {
        log.info("Enviando Estadio ID {} al microservicio local", request.getId());
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8086/api/estadios") 
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void enviarEntrenadorALocal(EntrenadorRequest request) {
        log.info("Enviando Entrenador ID {} al microservicio local", request.getId());
        webClientBuilder.build()
                .post()
                .uri("http://localhost:8087/api/entrenadores") 
                .bodyValue(request)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public List<ClubResponse> obtenerClubesLocales() {
        log.info("Obteniendo lista de clubes desde el microservicio local...");
        ClubResponse[] response = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/api/clubes") 
                .retrieve()
                .bodyToMono(ClubResponse[].class)
                .block();
        return response != null ? Arrays.asList(response) : List.of();
    }
}
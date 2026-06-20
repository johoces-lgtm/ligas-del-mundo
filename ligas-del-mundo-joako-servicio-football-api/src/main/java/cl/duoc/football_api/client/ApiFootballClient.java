package cl.duoc.football_api.client;

import cl.duoc.football_api.dto.external.ApiClubResponse;
import cl.duoc.football_api.dto.external.ApiEntrenadorResponse;
import cl.duoc.football_api.dto.external.ApiLeagueResponse;
import cl.duoc.football_api.dto.external.ApiPartidoResponse;
import cl.duoc.football_api.dto.external.ApiPosicionResponse;
import cl.duoc.football_api.dto.external.ApiJugadorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class ApiFootballClient {

    private final WebClient webClient;

    public ApiFootballClient(@Qualifier("apiFootballWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public ApiLeagueResponse obtenerLigaPorIdYTemporada(int idLiga, int temporada) {
        log.info("Saliendo a internet: Buscando liga (ID {}) para la temporada {}...", idLiga, temporada);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/leagues")
                        .queryParam("id", idLiga)
                        .queryParam("season", temporada)
                        .build())
                .retrieve()
                .bodyToMono(ApiLeagueResponse.class)
                .block();
    }

    public ApiClubResponse obtenerClubesPorLigaYTemporada(int idLiga, int temporada) {
        log.info("Saliendo a internet: Buscando clubes de la liga {} para la temporada {}...", idLiga, temporada);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams")
                        .queryParam("league", idLiga) 
                        .queryParam("season", temporada)
                        .build())
                .retrieve()
                .bodyToMono(ApiClubResponse.class)
                .block();
    }


    public ApiJugadorResponse obtenerJugadoresPorEquipo(Long idEquipo, String temporada, int pagina) {
        log.info("Saliendo a internet: Buscando jugadores del equipo (ID {}) para la temporada {}...", idEquipo, temporada);

        return webClient.get()
                .uri("/players?team=" + idEquipo + "&season=" + temporada + "&page=" + pagina) 
                .retrieve()
                .bodyToMono(ApiJugadorResponse.class)
                .block();
    }

    public ApiPosicionResponse obtenerPosicionesPorLiga(Integer idLiga, String temporada) {
        log.info("Saliendo a internet: Buscando posiciones de la liga {} para la temporada {}...", idLiga, temporada);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/standings")
                        .queryParam("league", idLiga)
                        .queryParam("season", temporada)
                        .build())
                .retrieve()
                .bodyToMono(ApiPosicionResponse.class)
                .block();
    }

    public ApiPartidoResponse obtenerPartidosPorLiga(Integer idLiga, String temporada) {
        log.info("Saliendo a internet: Buscando partidos de la liga {} para la temporada {}...", idLiga, temporada);

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures")
                        .queryParam("league", idLiga)
                        .queryParam("season", temporada)
                        .build())
                .retrieve()
                .bodyToMono(ApiPartidoResponse.class)
                .block();
    }

    public ApiEntrenadorResponse obtenerEntrenadoresPorEquipo(Long idEquipo) {
        log.info("Consultando API-Sports: Entrenadores del equipo ID {}", idEquipo);
        return webClient.get()
                .uri("/coachs?team={id}", idEquipo)
                .retrieve()
                .bodyToMono(ApiEntrenadorResponse.class)
                .block();
    }
}
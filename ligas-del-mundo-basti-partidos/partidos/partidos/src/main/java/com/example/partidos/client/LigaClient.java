package com.example.partidos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.partidos.dto.response.LigaDto;

@Component
public class LigaClient {

    private final WebClient webClient;

    public LigaClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://servicio-ligas").build();
    }

    public LigaDto obtenerLiga(Long id) {
        return webClient.get()
                .uri("/api/ligas/" + id)
                .retrieve()
                .bodyToMono(LigaDto.class)
                .block();
    }

    public void pingLigas() { 
        webClient.options().uri("/api/ligas").retrieve().toBodilessEntity().block(); 
    }
}
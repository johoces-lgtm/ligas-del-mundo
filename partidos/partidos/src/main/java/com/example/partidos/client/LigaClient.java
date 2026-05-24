package com.example.partidos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.partidos.dto.LigaDto;

@Component
public class LigaClient {

    private final WebClient webClient;

    public LigaClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public LigaDto obtenerLiga(Long id) {

        return webClient
                .get()
                .uri("http://localhost:8081/api/ligas/" + id)
                .retrieve()
                .bodyToMono(LigaDto.class)
                .block();
    }
}
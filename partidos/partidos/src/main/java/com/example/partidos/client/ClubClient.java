package com.example.partidos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.partidos.dto.ClubDto;

@Component
public class ClubClient {

    private final WebClient webClient;

    public ClubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ClubDto obtenerClub(Long id) {

        return webClient
                .get()
                .uri("http://localhost:8082/api/clubes/" + id)
                .retrieve()
                .bodyToMono(ClubDto.class)
                .block();
    }
}
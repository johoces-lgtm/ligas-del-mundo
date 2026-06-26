package com.example.partidos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.partidos.dto.response.ClubDto;

@Component
public class ClubClient {

    private final WebClient webClient;

    public ClubClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://servicio-clubes").build();
    }

    public ClubDto obtenerClub(Long id) {
        return webClient.get()
                .uri("/api/clubes/" + id)
                .retrieve()
                .bodyToMono(ClubDto.class)
                .block();
    }

    public void pingClubes() { 
        webClient.options().uri("/api/clubes").retrieve().toBodilessEntity().block(); 
    }
}
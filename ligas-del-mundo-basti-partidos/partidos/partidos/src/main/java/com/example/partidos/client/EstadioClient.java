package com.example.partidos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.partidos.dto.response.EstadioDto;

@Component
public class EstadioClient {

    private final WebClient webClient;

    public EstadioClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://servicio-estadios").build();
    }

    public EstadioDto obtenerEstadio(Long id) {
        return webClient.get()
                .uri("/api/estadios/" + id)
                .retrieve()
                .bodyToMono(EstadioDto.class)
                .block();
    }

    public void pingEstadios() {
        webClient.options().uri("/api/estadios").retrieve().toBodilessEntity().block();
    }
}
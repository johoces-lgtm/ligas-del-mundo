package com.example.posiciones.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.posiciones.dto.PartidoResponseDto;

@Component
public class PartidoClient {

    private final WebClient webClient;

    public PartidoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<PartidoResponseDto> obtenerPartidos() {

        PartidoResponseDto[] response = webClient
                .get()
                .uri("http://localhost:8088/api/partidos")
                .retrieve()
                .bodyToMono(PartidoResponseDto[].class)
                .block();

        return Arrays.asList(response);
    }
}
package com.example.posiciones.client;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.posiciones.dto.response.PartidoResponseDto;

@Component
public class PartidoClient {

    private final WebClient webClient;

    public PartidoClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8088/api/partidos").build();
    }

    public List<PartidoResponseDto> obtenerPartidos() {
        try {
            PartidoResponseDto[] response = webClient.get()
                    .retrieve()
                    .bodyToMono(PartidoResponseDto[].class)
                    .block();
            return Arrays.asList(response);
        } catch (Exception e) {
            return List.of();
        }
    }
}
package com.example.usuarios.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.example.usuarios.dto.response.DtoClubesResponse;

@Component
public class ClubesClient {

    private final WebClient webClient;

    public ClubesClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/api/clubes").build();
    }

    public DtoClubesResponse obtenerClubPorId(Long id) {
        try {
            return this.webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(DtoClubesResponse.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
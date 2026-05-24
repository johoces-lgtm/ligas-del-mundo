package com.example.partidos.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import lombok.extern.slf4j.Slf4j;
import com.example.partidos.dto.EstadioDto;

@Slf4j
@Component
public class EstadioClient {

    private final WebClient webClient;

    public EstadioClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public EstadioDto obtenerEstadio(Long id) {
        try {
            return webClient
                    .get()
                    .uri("http://localhost:8086/api/estadios/" + id)
                    .retrieve()
                    .bodyToMono(EstadioDto.class)
                    .block();
        } catch (Exception e) {
            log.warn("El microservicio de Estadios (8086) está apagado o no encontró el estadio ID {}. Dejando pasar por alto...", id);
            
            return null; 
        }
    }
}
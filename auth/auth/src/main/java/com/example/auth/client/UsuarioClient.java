package com.example.auth.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.example.auth.dto.response.UsuarioDto;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8085/api/usuarios").build();
    }

    public UsuarioDto obtenerUsuario(Long id) {
        try {
            return webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(UsuarioDto.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public UsuarioDto buscarPorCorreo(String correo) {
        try {
            return webClient.get()
                    .uri("/correo/{correo}", correo)
                    .retrieve()
                    .bodyToMono(UsuarioDto.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
}
package com.example.auth.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.auth.dto.UsuarioDto;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(WebClient.Builder webClientBuilder, @Value("${usuarios.url}") String urlUsuarios) {
        this.webClient = webClientBuilder.baseUrl(urlUsuarios).build();
    }

    public UsuarioDto obtenerUsuario(Long id) {
        return webClient.get()
                .uri("/api/usuarios/" + id)
                .retrieve()
                .bodyToMono(UsuarioDto.class)
                .block();
    }

    public UsuarioDto buscarPorCorreo(String correo) {
        try {
            return webClient.get()
                    .uri("/api/usuarios/correo/" + correo)
                    .retrieve()
                    .bodyToMono(UsuarioDto.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }
}
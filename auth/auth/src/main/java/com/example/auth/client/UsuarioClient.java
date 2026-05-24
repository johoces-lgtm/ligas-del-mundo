package com.example.auth.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.auth.dto.UsuarioDto;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public UsuarioDto obtenerUsuario(Long id) {
        return webClient.get()
                .uri("http://localhost:8091/api/usuarios/" + id)
                .retrieve()
                .bodyToMono(UsuarioDto.class)
                .block();
    }

    public UsuarioDto buscarPorCorreo(String correo) {
        return webClient.get()
                .uri("http://localhost:8091/api/usuarios/correo/" + correo)
                .retrieve()
                .bodyToMono(UsuarioDto.class)
                .block();
    }
}
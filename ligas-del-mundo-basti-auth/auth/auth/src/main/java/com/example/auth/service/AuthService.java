package com.example.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.auth.client.UsuarioClient;
import com.example.auth.dto.LoginRequestDto;
import com.example.auth.dto.LoginResponseDto;
import com.example.auth.dto.UsuarioDto;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioClient usuarioClient;

    public AuthService(JwtService jwtService, PasswordEncoder passwordEncoder, UsuarioClient usuarioClient) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioClient = usuarioClient;
    }

    public LoginResponseDto login(LoginRequestDto dto) {

        UsuarioDto usuario = usuarioClient.buscarPorCorreo(dto.getCorreo());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado o credenciales invalidas.");
        }


        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword_usuarios())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }


        String token = jwtService.generarToken(usuario.getCorreo_usuarios(), usuario.getRol_usuarios());

        return LoginResponseDto.builder()
                .token(token)
                .mensaje("Login correcto. Bienvenido " + usuario.getNombre_usuarios())
                .build();
    }
}
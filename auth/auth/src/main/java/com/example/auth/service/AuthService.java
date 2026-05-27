package com.example.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.auth.client.UsuarioClient;
import com.example.auth.dto.request.LoginRequestDto;
import com.example.auth.dto.response.LoginResponseDto;
import com.example.auth.dto.response.UsuarioDto;
import com.example.auth.exception.BadCredentialsException;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;
    private final UsuarioClient usuarioClient;

    public AuthService(JwtService jwtService, BCryptPasswordEncoder encoder, UsuarioClient usuarioClient) {
        this.jwtService = jwtService;
        this.encoder = encoder;
        this.usuarioClient = usuarioClient;
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        // 1. Ir a buscar al usuario real al puerto 8085 usando su correo
        UsuarioDto usuario = usuarioClient.buscarPorCorreo(dto.getCorreo());
        
        if (usuario == null) {
            throw new BadCredentialsException("Las credenciales ingresadas son incorrectas (Usuario no existe).");
        }

        // 2. Comparar el password en plano que mandan en Postman contra el BCrypt que guardaste en Usuarios
        if (!encoder.matches(dto.getPassword(), usuario.getPassword_usuarios())) {
            throw new BadCredentialsException("Las credenciales ingresadas son incorrectas (Contraseña no coincide).");
        }

        // 3. Si todo calza, se genera el token de sesión real
        String token = jwtService.generarToken(dto.getCorreo());

        return LoginResponseDto.builder()
                .token(token)
                .mensaje("Login correcto. Bienvenido " + usuario.getNombre_usuarios())
                .build();
    }
}
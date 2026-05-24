package com.example.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.dto.LoginRequestDto;
import com.example.auth.dto.LoginResponseDto;

@Service
public class AuthService {

    private final JwtService jwtService;

    private final BCryptPasswordEncoder encoder;

    public AuthService(
            JwtService jwtService,
            BCryptPasswordEncoder encoder) {

        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    public LoginResponseDto login(LoginRequestDto dto) {

        String token = jwtService.generarToken(dto.getCorreo());

        return LoginResponseDto.builder()
                .token(token)
                .mensaje("Login correcto")
                .build();
    }
}
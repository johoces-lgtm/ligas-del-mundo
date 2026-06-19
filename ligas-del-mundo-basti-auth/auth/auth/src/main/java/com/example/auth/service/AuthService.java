package com.example.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.auth.dto.LoginRequestDto;
import com.example.auth.dto.LoginResponseDto;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDto login(LoginRequestDto dto) {

        String token = jwtService.generarToken(dto.getCorreo());

        return LoginResponseDto.builder()
                .token(token)
                .mensaje("Login correcto")
                .build();
    }
}
package com.example.auth.controller;

import org.springframework.web.bind.annotation.*;
import com.example.auth.dto.LoginRequestDto;
import com.example.auth.dto.LoginResponseDto;
import com.example.auth.service.AuthService;

// IMPORTS DE SWAGGER OBLIGATORIOS
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
@Tag(name = "Controlador de Autenticación", description = "Endpoints para el manejo de sesiones e inicio de accesos al sistema")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión de usuario", 
        description = "Valida las credenciales otorgadas y genera un token JWT firmado de duración limitada si los datos coinciden."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa. Se retorna el token generado"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas o usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Falla de comunicación con el almacenamiento persistente de usuarios")
    })
    public LoginResponseDto login(@RequestBody LoginRequestDto dto) {
        return service.login(dto);
    }
}
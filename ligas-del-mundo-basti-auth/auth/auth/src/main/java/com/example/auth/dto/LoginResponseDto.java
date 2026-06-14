package com.example.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Respuesta del servidor con el token generado")
public class LoginResponseDto {

    @Schema(description = "Token JWT en formato String compacto", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Mensaje informativo sobre el estado del login", example = "Login correcto")
    private String mensaje;
}
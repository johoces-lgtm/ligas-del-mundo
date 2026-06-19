package com.example.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Modelo requerido para solicitar autenticación")
public class LoginRequestDto {

    @Schema(description = "Correo electrónico registrado del usuario", example = "goleador10@futbol.com")
    private String correo;

    @Schema(description = "Contraseña en texto plano para validar", example = "PasswordSegura123")
    private String password;
}
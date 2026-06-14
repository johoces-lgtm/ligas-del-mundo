package com.example.usuarios.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO que representa los datos necesarios para crear o actualizar un usuario")
public class UsuarioRequestDto {

    @NotBlank
    @Schema(description = "Nombre completo del usuario", example = "Bastian Alexander")
    private String nombre;

    @NotBlank
    @Schema(description = "Correo electrónico único para el inicio de sesión", example = "bastian@correo.com")
    private String correo;

    @NotBlank
    @Schema(description = "Contraseña de seguridad para la cuenta", example = "Password123!")
    private String password;

    @NotBlank
    @Schema(description = "Rol asignado al usuario en el sistema", example = "ROLE_USER")
    private String rol;

    @NotNull
    @Schema(description = "Identificador único del club favorito del usuario", example = "1")
    private Long clubFavoritoId;
}
package com.example.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Esquema mapeado del microservicio externo de Usuarios")
public class UsuarioDto {

    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo o nick del usuario", example = "Carlos Caszely")
    private String nombre_usuarios;

    @Schema(description = "Correo electrónico del usuario", example = "goleador10@futbol.com")
    private String correo_usuarios;

    @Schema(description = "Contraseña encriptada proveniente de la base de datos", example = "$2a$10$X5v...")
    private String password_usuarios;

    @Schema(description = "Rol asignado para los permisos en el sistema", example = "ROLE_USER")
    private String rol_usuarios;

    @Schema(description = "ID del club de fútbol favorito del usuario", example = "12")
    private Long clubFavoritoId;
}
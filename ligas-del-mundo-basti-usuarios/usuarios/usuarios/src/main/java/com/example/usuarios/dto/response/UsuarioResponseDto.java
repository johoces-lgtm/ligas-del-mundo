package com.example.usuarios.dto.response;

// IMPORTACIÓN DE SWAGGER
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO que representa la información pública o de respuesta de un usuario")
public class UsuarioResponseDto {

    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del usuario", example = "Bastian Alexander")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "bastian@correo.com")
    private String correo;

    @Schema(description = "Rol del usuario en la plataforma", example = "ROLE_USER")
    private String rol;

    @Schema(description = "ID del club favorito seleccionado por el usuario", example = "1")
    private Long clubFavoritoId;
}
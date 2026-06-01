package com.example.usuarios.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDto {
    private Long id;
    private String nombre;
    private String correo;
    private String rol;
    private Long clubFavoritoId;
}
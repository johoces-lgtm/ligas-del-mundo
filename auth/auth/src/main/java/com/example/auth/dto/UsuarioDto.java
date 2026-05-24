package com.example.auth.dto;

import lombok.Data;

@Data
public class UsuarioDto {

    private Long id;

    private String nombre_usuarios;

    private String correo_usuarios;

    private String password_usuarios;

    private String rol_usuarios;

    private Long clubFavoritoId;
}
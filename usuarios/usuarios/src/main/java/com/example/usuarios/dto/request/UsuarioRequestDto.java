package com.example.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UsuarioRequestDto {

    @NotBlank
    private String nombre;

    @NotBlank
    private String correo;

    @NotBlank
    private String password;

    @NotBlank
    private String rol;

    @NotNull
    private Long clubFavoritoId;
}
package com.example.usuarios.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty("nombre_usuarios")
    private String nombre;

    @Column(nullable = false, unique = true)
    @JsonProperty("correo_usuarios")
    private String correo;

    @Column(nullable = false)
    @JsonProperty("password_usuarios")
    private String password;

    @Column(nullable = false)
    @JsonProperty("rol_usuarios")
    private String rol;

    @Column(nullable = false)
    private Long clubFavoritoId;
}
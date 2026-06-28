package com.example.usuarios.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.usuarios.model.Usuario;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuarioBase;

    @BeforeEach
    void setUp() {
        usuarioBase = new Usuario();
        usuarioBase.setNombre("Bastian Alexander");
        usuarioBase.setCorreo("bastian@correo.com");
        usuarioBase.setPassword("Password123!");
        usuarioBase.setRol("ROLE_USER");
        usuarioBase.setClubFavoritoId(10L); 

        usuarioRepository.save(usuarioBase);
    }

    @Test
    void encontrarPorId_CuandoUsuarioExiste_RetornaUsuario() {
        Long idBuscado = 1L;

        var resultado = usuarioRepository.findById(idBuscado);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Bastian Alexander");
        assertThat(resultado.get().getClubFavoritoId()).isEqualTo(10L);
    }

    @Test
    void count_debeRetornarCeroCuandoLaBaseDeDatosEstaVacia() {

        long totalUsuarios = usuarioRepository.count();

        org.assertj.core.api.Assertions.assertThat(totalUsuarios).isEqualTo(1L);
    }
}
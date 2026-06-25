package com.example.posiciones.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.posiciones.model.Posicion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class PosicionRepositoryTest {

    @Autowired
    private PosicionRepository repository;

    private Posicion posicion;

    @BeforeEach
    void setUp() {

        posicion = Posicion.builder()
                .clubId(1L)
                .nombreClub("Colo Colo")
                .puntos(25)
                .partidosJugados(10)
                .ganados(8)
                .empatados(1)
                .perdidos(1)
                .golesFavor(20)
                .golesContra(5)
                .diferenciaGoles(15)
                .temporada(2026)
                .ligaId(3L)
                .build();

        repository.save(posicion);
    }

    @Test
    void buscarPorId_retornaPosicion() {

        Long id = repository.findAll().get(0).getId();

        var resultado = repository.findById(id);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombreClub())
                .isEqualTo("Colo Colo");
    }
}
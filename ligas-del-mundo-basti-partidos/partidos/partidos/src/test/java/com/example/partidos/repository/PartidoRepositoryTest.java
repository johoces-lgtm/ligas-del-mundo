package com.example.partidos.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.partidos.model.Partido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class PartidoRepositoryTest {

    @Autowired
    private PartidoRepository repository;

    @Test
    void debeGuardarPartido() {

        Partido partido = Partido.builder()
                .ligaId(1L)
                .clubLocalId(1L)
                .clubVisitaId(2L)
                .estadioId(1L)
                .nombreLocal("Colo Colo")
                .nombreVisita("La U")
                .golesLocal(2)
                .golesVisita(1)
                .estado("FINALIZADO")
                .temporada(2026)
                .build();

        Partido guardado = repository.save(partido);

        assertNotNull(guardado.getId());
    }

    @Test
    void existsById_debeRetornarFalsoSiNoExiste() {
        // GIVEN: Un ID que sabemos que no existe en la base de datos H2 vacía
        Long idInexistente = 999L;

        // WHEN: Usamos el método nativo de JPA para verificar existencia
        boolean existe = repository.existsById(idInexistente);

        // THEN: El repositorio debe confirmar que el registro no existe
        org.assertj.core.api.Assertions.assertThat(existe).isFalse();
    }
}
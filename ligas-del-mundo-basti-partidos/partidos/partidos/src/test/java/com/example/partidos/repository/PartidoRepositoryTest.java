package com.example.partidos.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.partidos.model.Partido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
}
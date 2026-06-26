package com.example.partidos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.example.partidos.client.*;
import com.example.partidos.dto.request.PartidoRequestDto;
import com.example.partidos.dto.response.*;
import com.example.partidos.model.Partido;
import com.example.partidos.repository.PartidoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class PartidoServiceTest {

    @Mock
    private PartidoRepository repository;

    @Mock
    private LigaClient ligaClient;

    @Mock
    private ClubClient clubClient;

    @Mock
    private EstadioClient estadioClient;

    @InjectMocks
    private PartidoService service;

    private Partido partido;

    @BeforeEach
    void setUp() {

        partido = Partido.builder()
                .id(1L)
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
    }

    @Test
    void debeBuscarPartido() {

        when(repository.findById(1L))
                .thenReturn(Optional.of(partido));

        when(ligaClient.obtenerLiga(anyLong()))
                .thenReturn(new LigaDto());

        when(clubClient.obtenerClub(anyLong()))
                .thenReturn(new ClubDto());

        when(estadioClient.obtenerEstadio(anyLong()))
                .thenReturn(new EstadioDto());

        Partido resultado = service.buscar(1L);

        assertEquals(1L, resultado.getId());
    }
}
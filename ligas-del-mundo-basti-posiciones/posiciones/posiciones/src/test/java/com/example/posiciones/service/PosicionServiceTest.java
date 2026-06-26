package com.example.posiciones.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import com.example.posiciones.client.ClubesClient;
import com.example.posiciones.client.PartidoClient;
import com.example.posiciones.dto.request.PosicionRequestDto;
import com.example.posiciones.dto.response.DtoClubesResponse;
import com.example.posiciones.dto.response.PartidoResponseDto;
import com.example.posiciones.exception.ResourceNotFoundException;
import com.example.posiciones.model.Posicion;
import com.example.posiciones.repository.PosicionRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PosicionServiceTest {

    @Mock
    private PosicionRepository repository;

    @Mock
    private PartidoClient partidoClient;

    @Mock
    private ClubesClient clubesClient;

    @InjectMocks
    private PosicionService service;

    @Test
    void guardar_debeLanzarErrorSiClubNoExiste() {

        PosicionRequestDto dto = new PosicionRequestDto();
        dto.setClubId(999L);

        when(clubesClient.obtenerClubPorId(999L))
                .thenReturn(null);

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.guardar(dto)
        );

        verify(repository, never()).save(any());
    }

    @Test
    void guardar_debeGuardarSiClubExiste() {

        PosicionRequestDto dto = new PosicionRequestDto();

        dto.setClubId(1L);
        dto.setPuntos(25);
        dto.setPartidosJugados(10);
        dto.setGanados(8);
        dto.setEmpatados(1);
        dto.setPerdidos(1);
        dto.setGolesFavor(20);
        dto.setGolesContra(5);
        dto.setDiferenciaGoles(15);
        dto.setTemporada(2026);
        dto.setLigaId(3L);

        DtoClubesResponse club = new DtoClubesResponse();
        club.setNombre("Colo Colo");

        when(clubesClient.obtenerClubPorId(1L))
                .thenReturn(club);

        when(repository.save(any(Posicion.class)))
                .thenAnswer(i -> i.getArgument(0));

        Posicion resultado = service.guardar(dto);

        assertNotNull(resultado);

        verify(repository, times(1))
                .save(any(Posicion.class));
    }

    @Test
    void obtenerPartidos_retornaLista() {

        PartidoResponseDto partido =
                new PartidoResponseDto();

        when(partidoClient.obtenerPartidos())
                .thenReturn(List.of(partido));

        var resultado = service.obtenerPartidos();

        assertEquals(1, resultado.size());
    }

    @Test
    void eliminar_debeEliminar() {

        when(repository.existsById(1L))
                .thenReturn(true);

        service.eliminar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void eliminar_debeLanzarErrorSiNoExiste() {

        when(repository.existsById(1L))
                .thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.eliminar(1L)
        );
    }
}
package com.example.posiciones.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.posiciones.client.PartidoClient;
import com.example.posiciones.dto.PartidoResponseDto;
import com.example.posiciones.dto.PosicionRequestDto;
import com.example.posiciones.model.Posicion;
import com.example.posiciones.repository.PosicionRepository;

@Service
public class PosicionService {

    private final PosicionRepository repository;
    private final PartidoClient partidoClient;

    public PosicionService(PosicionRepository repository, PartidoClient partidoClient) {
        this.repository = repository;
        this.partidoClient = partidoClient;
    }

    public List<Posicion> listar() {
        return repository.findAll();
    }

    public Posicion buscar(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Posicion guardar(PosicionRequestDto dto) {
        Posicion posicion = Posicion.builder()
                .clubId(dto.getClubId())
                .nombreClub(dto.getNombreClub())
                .puntos(dto.getPuntos())
                .partidosJugados(dto.getPartidosJugados())
                .ganados(dto.getGanados())
                .empatados(dto.getEmpatados())
                .perdidos(dto.getPerdidos())
                .golesFavor(dto.getGolesFavor())
                .golesContra(dto.getGolesContra())
                .diferenciaGoles(dto.getDiferenciaGoles())
                .temporada(dto.getTemporada())
                .ligaId(dto.getLigaId())
                .build();
        return repository.save(posicion);
    }

    public Posicion actualizar(Long id, PosicionRequestDto dto) {
        Posicion posicion = repository.findById(id).orElse(null);
        if (posicion != null) {
            posicion.setClubId(dto.getClubId());
            posicion.setNombreClub(dto.getNombreClub());
            posicion.setPuntos(dto.getPuntos());
            posicion.setPartidosJugados(dto.getPartidosJugados());
            posicion.setGanados(dto.getGanados());
            posicion.setEmpatados(dto.getEmpatados());
            posicion.setPerdidos(dto.getPerdidos());
            posicion.setGolesFavor(dto.getGolesFavor());
            posicion.setGolesContra(dto.getGolesContra());
            posicion.setDiferenciaGoles(dto.getDiferenciaGoles());
            posicion.setTemporada(dto.getTemporada());
            posicion.setLigaId(dto.getLigaId());
            return repository.save(posicion);
        }
        return null;
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public List<PartidoResponseDto> obtenerPartidos() {
        return partidoClient.obtenerPartidos();
    }
}
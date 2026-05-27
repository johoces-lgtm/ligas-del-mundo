package com.example.partidos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.partidos.client.ClubClient;
import com.example.partidos.client.EstadioClient;
import com.example.partidos.client.LigaClient;
import com.example.partidos.dto.request.PartidoRequestDto;
import com.example.partidos.model.Partido;
import com.example.partidos.repository.PartidoRepository;
import com.example.partidos.exception.ResourceNotFoundException;

@Service
public class PartidoService {

    private final PartidoRepository repository;
    private final LigaClient ligaClient;
    private final ClubClient clubClient;
    private final EstadioClient estadioClient;

    public PartidoService(PartidoRepository repository, LigaClient ligaClient, ClubClient clubClient, EstadioClient estadioClient) {
        this.repository = repository;
        this.ligaClient = ligaClient;
        this.clubClient = clubClient;
        this.estadioClient = estadioClient;
    }

    public List<Partido> listar() {
        return repository.findAll();
    }

    public Partido buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Partido no encontrado con ID: " + id));
    }

    public Partido guardar(PartidoRequestDto dto) {
        ligaClient.obtenerLiga(dto.getLigaId());
        clubClient.obtenerClub(dto.getClubLocalId());
        clubClient.obtenerClub(dto.getClubVisitaId());
        estadioClient.obtenerEstadio(dto.getEstadioId());

        Partido partido = Partido.builder()
                .ligaId(dto.getLigaId())
                .clubLocalId(dto.getClubLocalId())
                .clubVisitaId(dto.getClubVisitaId())
                .estadioId(dto.getEstadioId())
                .nombreLocal(dto.getNombreLocal())
                .nombreVisita(dto.getNombreVisita())
                .golesLocal(dto.getGolesLocal())
                .golesVisita(dto.getGolesVisita())
                .estado(dto.getEstado())
                .temporada(dto.getTemporada())
                .build();
        return repository.save(partido);
    }

    public Partido actualizar(Long id, PartidoRequestDto dto) {
        Partido partido = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Partido no encontrado con ID: " + id));
        
        partido.setLigaId(dto.getLigaId());
        partido.setClubLocalId(dto.getClubLocalId());
        partido.setClubVisitaId(dto.getClubVisitaId());
        partido.setEstadioId(dto.getEstadioId());
        partido.setNombreLocal(dto.getNombreLocal());
        partido.setNombreVisita(dto.getNombreVisita());
        partido.setGolesLocal(dto.getGolesLocal());
        partido.setGolesVisita(dto.getGolesVisita());
        partido.setEstado(dto.getEstado());
        partido.setTemporada(dto.getTemporada());
        return repository.save(partido);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Partido no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }
}
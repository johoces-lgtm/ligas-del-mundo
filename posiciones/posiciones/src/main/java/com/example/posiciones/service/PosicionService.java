package com.example.posiciones.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.posiciones.client.PartidoClient;
import com.example.posiciones.client.ClubesClient;
import com.example.posiciones.dto.response.PartidoResponseDto;
import com.example.posiciones.dto.response.DtoClubesResponse;
import com.example.posiciones.dto.request.PosicionRequestDto;
import com.example.posiciones.model.Posicion;
import com.example.posiciones.repository.PosicionRepository;
import com.example.posiciones.exception.ResourceNotFoundException;

@Service
public class PosicionService {

    private final PosicionRepository repository;
    private final PartidoClient partidoClient;
    private final ClubesClient clubesClient;

    public PosicionService(PosicionRepository repository, PartidoClient partidoClient, ClubesClient clubesClient) {
        this.repository = repository;
        this.partidoClient = partidoClient;
        this.clubesClient = clubesClient;
    }

    public List<Posicion> listar() {
        return repository.findAll();
    }

    public Posicion buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de posición no encontrado con ID: " + id));
    }

    public Posicion guardar(PosicionRequestDto dto) {
        // Validación Inter-servicio: Verificar que el club exista en servicio-clubes (8082)
        DtoClubesResponse club = clubesClient.obtenerClubPorId(dto.getClubId());
        if (club == null) {
            throw new ResourceNotFoundException("No se puede crear el registro de posición. El Club con ID: " 
                    + dto.getClubId() + " no existe en el sistema de Clubes.");
        }

        Posicion posicion = Posicion.builder()
                .clubId(dto.getClubId())
                .nombreClub(club.getNombre())
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
        Posicion posicion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar. Registro no encontrado con ID: " + id));

        DtoClubesResponse club = clubesClient.obtenerClubPorId(dto.getClubId());
        if (club == null) {
            throw new ResourceNotFoundException("No se puede actualizar la posición. El Club con ID: " 
                    + dto.getClubId() + " no existe en el sistema de Clubes.");
        }

        posicion.setClubId(dto.getClubId());
        posicion.setNombreClub(club.getNombre());
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

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Registro no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    public List<PartidoResponseDto> obtenerPartidos() {
        return partidoClient.obtenerPartidos();
    }
}
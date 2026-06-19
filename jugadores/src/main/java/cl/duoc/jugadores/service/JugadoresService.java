package cl.duoc.jugadores.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.jugadores.dto.request.DtoJugadoresRequest;
import cl.duoc.jugadores.dto.response.DtoJugadoresResponse;
import cl.duoc.jugadores.model.JugadoresModel;
import cl.duoc.jugadores.repository.JugadoresRepository;
import cl.duoc.jugadores.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JugadoresService {

    @Autowired
    private JugadoresRepository repository;

    @Autowired
    private cl.duoc.jugadores.client.ClubesClient clubesClient;

    private DtoJugadoresResponse mapearAModeloResponse(JugadoresModel model) {
        DtoJugadoresResponse response = new DtoJugadoresResponse();
        response.setId(model.getId());
        response.setNombre(model.getNombre());
        response.setNacionalidad(model.getNacionalidad());
        response.setPosicion(model.getPosicion());
        response.setEdad(model.getEdad());
        response.setFotoUrl(model.getFotoUrl());
        response.setClubId(model.getClubId());
        return response;
    }

    public List<DtoJugadoresResponse> obtenerTodos(){
        clubesClient.pingClubes();
        return repository.findAll().stream().map(this::mapearAModeloResponse).collect(Collectors.toList());
    }

    public DtoJugadoresResponse obtenerPorId(Long id) {
        JugadoresModel jugador = repository.findById(id).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        if (!clubesClient.validarClub(jugador.getClubId())) {
            throw new ResourceNotFoundException("Inconsistencia: El club de este jugador ya no existe.");
        }
        return mapearAModeloResponse(jugador);
    }

    public List<DtoJugadoresResponse> obtenerPorClub(Long clubId) {
        log.info("Buscando jugadores del club: {}", clubId);
        return repository.findByClubId(clubId).stream()
                .map(this::mapearAModeloResponse)
                .collect(Collectors.toList());
    }

    public DtoJugadoresResponse guardar(DtoJugadoresRequest request) {
        log.info("Guardando nuevo jugador: {}", request.getNombre());
        
        boolean existeElClub = clubesClient.validarClub(request.getClubId());
        
        if (!existeElClub) {
            throw new ResourceNotFoundException("No se puede registrar al jugador. El club con ID " + request.getClubId() + " no existe.");
        }

        JugadoresModel jugador = new JugadoresModel();
        jugador.setId(request.getId());
        jugador.setNombre(request.getNombre());
        jugador.setNacionalidad(request.getNacionalidad());
        jugador.setPosicion(request.getPosicion());
        jugador.setEdad(request.getEdad());
        jugador.setFotoUrl(request.getFotoUrl());
        jugador.setClubId(request.getClubId());

        return mapearAModeloResponse(repository.save(jugador));
    }

    public void eliminar(Long id) {
        log.info("Eliminando jugador ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: Jugador no encontrado");
        }
        repository.deleteById(id);
    }

    

}
package cl.duoc.lesiones.service;

import cl.duoc.lesiones.client.JugadoresClient;
import cl.duoc.lesiones.dto.request.DtoLesionesRequest;
import cl.duoc.lesiones.dto.response.DtoLesionesResponse;
import cl.duoc.lesiones.exception.ResourceNotFoundException;
import cl.duoc.lesiones.model.LesionesModel;
import cl.duoc.lesiones.repository.LesionesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LesionesService {

    @Autowired
    private LesionesRepository repository;

    @Autowired
    private JugadoresClient jugadoresClient;

    public List<DtoLesionesResponse> obtenerTodas() {
        jugadoresClient.pingJugadores();
        return repository.findAll().stream().map(this::mapearAResponse).collect(Collectors.toList());
    }

    public DtoLesionesResponse guardar(DtoLesionesRequest request) {
        log.info("Registrando nueva lesión para el jugador ID: {}", request.getJugadorId());

        boolean existeJugador = jugadoresClient.validarJugador(request.getJugadorId());
        
        if (!existeJugador) {
            throw new ResourceNotFoundException("Imposible registrar lesión: El jugador con ID " + request.getJugadorId() + " no existe.");
        }

        LesionesModel lesion = mapearAEntidad(request);

        return mapearAResponse(repository.save(lesion));
    }

    public DtoLesionesResponse actualizar(Long id, DtoLesionesRequest request) {
        LesionesModel lesion = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La lesión con ID " + id + " no existe."));
        lesion.setTipoLesion(request.getTipoLesion());
        lesion.setGravedad(request.getGravedad());
        lesion.setFechaInicio(request.getFechaInicio());
        lesion.setFechaEstimadaRecuperacion(request.getFechaEstimadaRecuperacion());
        lesion.setJugadorId(request.getJugadorId());
        return mapearAResponse(repository.save(lesion));
    }

    public List<DtoLesionesResponse> obtenerPorJugador(Long jugadorId) {
        if (!jugadoresClient.validarJugador(jugadorId)) {
             throw new ResourceNotFoundException("El jugador no existe o está inactivo.");
        }
        return repository.findByJugadorId(jugadorId).stream().map(this::mapearAResponse).collect(Collectors.toList());
    }

    private DtoLesionesResponse mapearAResponse(LesionesModel model) {
        DtoLesionesResponse dto = new DtoLesionesResponse();
        dto.setId(model.getId());
        dto.setTipoLesion(model.getTipoLesion());
        dto.setGravedad(model.getGravedad());
        dto.setFechaInicio(model.getFechaInicio());
        dto.setFechaEstimadaRecuperacion(model.getFechaEstimadaRecuperacion());
        dto.setJugadorId(model.getJugadorId());
        return dto;
    }

    private LesionesModel mapearAEntidad(DtoLesionesRequest request) {
        LesionesModel model = new LesionesModel();
        model.setTipoLesion(request.getTipoLesion());
        model.setGravedad(request.getGravedad());
        model.setFechaInicio(request.getFechaInicio());
        model.setFechaEstimadaRecuperacion(request.getFechaEstimadaRecuperacion());
        model.setJugadorId(request.getJugadorId());
        return model;
    }
}
package duoc.cl.entrenadores.service;

import duoc.cl.entrenadores.dto.response.DtoControllerEntrenadores;
import duoc.cl.entrenadores.client.ClubClient;
import duoc.cl.entrenadores.dto.request.DtoEntrenadorRequest;
import duoc.cl.entrenadores.exception.ResourceNotFoundException;
import duoc.cl.entrenadores.model.EntrenadorModel;
import duoc.cl.entrenadores.repository.EntrenadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntrenadorService {

    private final EntrenadorRepository repository;
    private final ClubClient clubClient;

    public DtoControllerEntrenadores crear(DtoEntrenadorRequest request) {
        log.info("Iniciando creación de entrenador: {}", request.getNombre());

        boolean existeElClub = clubClient.validarClub(request.getIdClub());

        if (!existeElClub) {
            log.error("Validación fallida: El club con ID {} no existe.", request.getIdClub());
            throw new ResourceNotFoundException("No se puede registrar al entrenador. El club con ID " + request.getIdClub() + " no existe.");
        }

        EntrenadorModel model = new EntrenadorModel();
        model.setId(request.getId());
        model.setNombre(request.getNombre());
        model.setNacionalidad(request.getNacionalidad());
        model.setIdClub(request.getIdClub());

        EntrenadorModel guardado = repository.save(model);
        log.info("Entrenador guardado exitosamente con ID: {}", guardado.getId());

        return mapearAResponse(guardado);
    }

    public List<DtoControllerEntrenadores> listar() {
        log.info("Recuperando listado de entrenadores");
        return repository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public DtoControllerEntrenadores buscarPorId(Long id) {
        log.info("Buscando entrenador con ID: {}", id);
        EntrenadorModel model = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Entrenador no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Entrenador no encontrado con ID: " + id);
                });
        
        return mapearAResponse(model);
    }

    private DtoControllerEntrenadores mapearAResponse(EntrenadorModel model) {
        return DtoControllerEntrenadores.builder()
                .id(model.getId())
                .nombre(model.getNombre())
                .nacionalidad(model.getNacionalidad())
                .build();
    }
}
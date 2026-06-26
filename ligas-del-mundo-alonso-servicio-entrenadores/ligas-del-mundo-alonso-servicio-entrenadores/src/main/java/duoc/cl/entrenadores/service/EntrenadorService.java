package duoc.cl.entrenadores.service;

import duoc.cl.entrenadores.dto.response.DtoEntrenadorResponse;
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

    public DtoEntrenadorResponse crear(DtoEntrenadorRequest request) {
        log.info("Iniciando creacion de entrenador: {}", request.getNombre());

        boolean existeElClub = clubClient.validarClub(request.getIdClub());

        if (!existeElClub) {
            log.error("Validacion fallida: El club con ID {} no existe en la base central.", request.getIdClub());
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

    public List<DtoEntrenadorResponse> listar() {
        log.info("Recuperando listado completo de entrenadores");
        return repository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public DtoEntrenadorResponse buscarPorId(Long id) {
        log.info("Buscando entrenador con ID: {}", id);
        EntrenadorModel model = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Entrenador no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("Entrenador no encontrado con ID: " + id);
                });
        
        return mapearAResponse(model);
    }

    public void eliminar(Long id) {
<<<<<<< HEAD
        EntrenadorModel entrenador = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El entrenador con ID " + id + " no existe."));
        repository.delete(entrenador);
=======
        log.info("Eliminando de persistencia el entrenador con ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Entrenador no encontrado con ID: " + id);
        }
        repository.deleteById(id);
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
    }

    private DtoEntrenadorResponse mapearAResponse(EntrenadorModel model) {
        return DtoEntrenadorResponse.builder()
                .id(model.getId())
                .nombre(model.getNombre())
                .nacionalidad(model.getNacionalidad())
                .idClub(model.getIdClub())
                .build();
    }
}
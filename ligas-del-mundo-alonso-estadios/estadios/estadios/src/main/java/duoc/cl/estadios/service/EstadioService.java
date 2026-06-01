package duoc.cl.estadios.service;

import duoc.cl.estadios.dto.response.DtoEstadioResponse;
import duoc.cl.estadios.exception.ResourceNotFoundException;
import duoc.cl.estadios.client.PaisClient;
import duoc.cl.estadios.dto.request.DtoEstadioRequest;
import duoc.cl.estadios.model.EstadioModel;
import duoc.cl.estadios.repository.EstadioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstadioService {
    
    private final EstadioRepository estadioRepository;
    private final PaisClient paisClient;

    public DtoEstadioResponse crearEstadio(DtoEstadioRequest request) {
        log.info("Iniciando creación de estadio ID: {}. Validando país ID: {}", request.getId(), request.getIdPais());
        
        Boolean paisValido = paisClient.validarPais(request.getIdPais()).block();
        
        if (Boolean.FALSE.equals(paisValido)) {
            log.error("Validación fallida: País con ID {} no existe", request.getIdPais());
            throw new ResourceNotFoundException("El país referenciado no existe.");
        }

        EstadioModel modelo = new EstadioModel();
        modelo.setId(request.getId());
        modelo.setNombre(request.getNombre());
        modelo.setCapacidad(request.getCapacidad());
        modelo.setIdPais(request.getIdPais());
        
        EstadioModel guardado = estadioRepository.save(modelo);
        log.info("Estadio guardado exitosamente: {}", guardado.getNombre());
        
        return mapearAResponse(guardado);
    }

    public DtoEstadioResponse obtenerEstadioPorId(Long id) {
        EstadioModel modelo = estadioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Estadio no encontrado"));
        if (!paisClient.validarPais(modelo.getIdPais())) {
            throw new ResourceNotFoundException("El país de este estadio ya no existe.");
        }
        return mapearAResponse(modelo);
    }

    public List<DtoEstadioResponse> obtenerTodos() {
        paisClient.pingPaises(); // PING
        return estadioRepository.findAll().stream().map(this::mapearAResponse).collect(Collectors.toList());
    }

    private DtoEstadioResponse mapearAResponse(EstadioModel modelo) {
        return DtoEstadioResponse.builder()
                .id(modelo.getId())
                .nombre(modelo.getNombre())
                .capacidad(modelo.getCapacidad())
                .idPais(modelo.getIdPais())
                .build();
    }
}
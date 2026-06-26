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
        log.info("Iniciando creacion de estadio ID: {}. Validando pais ID: {}", request.getId(), request.getIdPais());
        
        boolean paisValido = paisClient.validarPais(request.getIdPais());
        
        if (!paisValido) {
            log.error("Validacion fallida: Pais con ID {} no existe en servicio-paises", request.getIdPais());
            throw new ResourceNotFoundException("El pais referenciado no existe.");
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
        EstadioModel modelo = estadioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estadio no encontrado con ID: " + id));
        
        if (!paisClient.validarPais(modelo.getIdPais())) {
            throw new ResourceNotFoundException("El pais de este estadio ya no existe o no se encuentra disponible.");
        }
        return mapearAResponse(modelo);
    }

    public List<DtoEstadioResponse> obtenerTodos() {
        paisClient.pingPaises(); 
        return estadioRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        log.info("Eliminando estadio con ID: {}", id);
        EstadioModel estadio = estadioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El estadio con ID " + id + " no existe."));
        
        estadioRepository.delete(estadio);
        log.info("Estadio eliminado exitosamente");
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
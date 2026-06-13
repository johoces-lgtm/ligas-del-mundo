package duoc.cl.paises.service;

import duoc.cl.paises.dto.request.DtoPaisRequest;
import duoc.cl.paises.dto.response.DtoPaisResponse;
import duoc.cl.paises.exception.ResourceNotFoundException;
import duoc.cl.paises.model.PaisModel;
import duoc.cl.paises.repository.PaisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaisService {
    
    private final PaisRepository paisRepository;

    public DtoPaisResponse crearPais(DtoPaisRequest request) {
        log.info("Iniciando creación/actualización de país con ID: {}", request.getId());
        
        PaisModel modelo = new PaisModel();
        modelo.setId(request.getId());
        modelo.setNombre(request.getNombre());
        modelo.setCodigoIso(request.getCodigoIso());
        modelo.setUrlBandera(request.getUrlBandera());
        
        PaisModel guardado = paisRepository.save(modelo);
        log.info("País guardado exitosamente: {}", guardado.getNombre());
        
        return mapearAResponse(guardado);
    }

    public DtoPaisResponse obtenerPaisPorId(Long id) {
        log.info("Buscando país por ID: {}", id);
        PaisModel modelo = paisRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("País no encontrado con ID: {}", id);
                    return new ResourceNotFoundException("País no encontrado");
                });
        return mapearAResponse(modelo);
    }

    public List<DtoPaisResponse> obtenerTodos() {
        log.info("Obteniendo listado de todos los países");
        return paisRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private DtoPaisResponse mapearAResponse(PaisModel modelo) {
        return DtoPaisResponse.builder()
                .id(modelo.getId())
                .nombre(modelo.getNombre())
                .codigoIso(modelo.getCodigoIso())
                .urlBandera(modelo.getUrlBandera())
                .build();
    }
}
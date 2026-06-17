package duoc.cl.clubes.service;

import duoc.cl.clubes.client.LigasClient;
import duoc.cl.clubes.dto.request.DtoClubesRequest;
import duoc.cl.clubes.dto.response.DtoClubesResponse;
import duoc.cl.clubes.exception.ResourceNotFoundException;
import duoc.cl.clubes.model.ClubesModel;
import duoc.cl.clubes.repository.ClubesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClubesService {

    @Autowired
    private ClubesRepository clubesRepository;

    @Autowired
    private LigasClient ligasClient;

    public List<DtoClubesResponse> listarTodos() {
        log.info("Consultando todos los clubes");
        return clubesRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public DtoClubesResponse buscarPorId(Long id) {
        ClubesModel club = clubesRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Club con ID {} no encontrado", id);
                    return new ResourceNotFoundException("Club no encontrado con ID: " + id);
                });
        return mapearAResponse(club);
    }

    public DtoClubesResponse crear(DtoClubesRequest request) {
        log.info("Intentando crear club: {} para la liga ID: {}", request.getNombre(), request.getLigaId());

        boolean existeLiga = ligasClient.validarLiga(request.getLigaId());

        if (!existeLiga) {
            throw new ResourceNotFoundException("No se puede crear el club. La liga indicada (ID " + request.getLigaId() + ") no existe.");
        }

        ClubesModel club = new ClubesModel();
        club.setId(request.getId());
        club.setNombre(request.getNombre());
        club.setLogoUrl(request.getLogoUrl());
        club.setAnioFundacion(request.getAnioFundacion());
        club.setEstadioNombre(request.getEstadioNombre());
        club.setLigaId(request.getLigaId());

        log.info("Liga validada exitosamente. Procediendo a guardar el club.");
        return mapearAResponse(clubesRepository.save(club));
    }

    public void eliminar(Long id) {
        if (!clubesRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: Club no encontrado");
        }
        clubesRepository.deleteById(id);
        log.info("Club con ID {} eliminado correctamente", id);
    }

    private DtoClubesResponse mapearAResponse(ClubesModel model) {
        DtoClubesResponse response = new DtoClubesResponse();
        response.setId(model.getId());
        response.setNombre(model.getNombre());
        response.setLogoUrl(model.getLogoUrl());
        response.setAnioFundacion(model.getAnioFundacion());
        response.setEstadioNombre(model.getEstadioNombre());
        response.setLigaId(model.getLigaId());
        return response;
    }
}
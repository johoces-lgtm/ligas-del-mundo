package cl.duoc.ligas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.ligas.dto.request.DtoLigasRequest; // IMPORTANTE AGREGAR ESTO
import cl.duoc.ligas.dto.response.DtoLigasResponse;
import cl.duoc.ligas.exception.ResourceNotFoundException;
import cl.duoc.ligas.model.LigasModel;
import cl.duoc.ligas.repository.LigasRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LigasService {

    @Autowired
    private LigasRepository ligasrepository;

    public List<DtoLigasResponse> obtenerTodasLasLigas(){
        log.info("Obteniendo todas las ligas de la base de datos");
        List<LigasModel> ligas = ligasrepository.findAll();

        return ligas.stream()
                .map(liga -> {
                    DtoLigasResponse response = new DtoLigasResponse();
                    response.setId(liga.getId());
                    response.setNombre(liga.getNombre());
                    response.setPais(liga.getPais());
                    response.setLogoUrl(liga.getLogoUrl());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public DtoLigasResponse obtenerLigaPorId(Long id){
        log.info("Buscando liga con ID: {}", id);
        
        LigasModel liga = ligasrepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada con ID: " + id));
                
        DtoLigasResponse response = new DtoLigasResponse();
        response.setId(liga.getId()); 
        response.setNombre(liga.getNombre());
        response.setPais(liga.getPais());
        response.setLogoUrl(liga.getLogoUrl());
        return response;
    }

    // CORRECCIÓN AQUÍ: Recibir DtoLigasRequest
    public DtoLigasResponse crearLiga(DtoLigasRequest requestDto){
        log.info("Creando nueva liga con nombre: {}", requestDto.getNombre());
        LigasModel nuevaLiga = new LigasModel();
        
        nuevaLiga.setId(requestDto.getId()); 
        nuevaLiga.setNombre(requestDto.getNombre());
        nuevaLiga.setPais(requestDto.getPais());
        nuevaLiga.setLogoUrl(requestDto.getLogoUrl());

        LigasModel ligaGuardada = ligasrepository.save(nuevaLiga);

        DtoLigasResponse response = new DtoLigasResponse();
        response.setId(ligaGuardada.getId());
        response.setNombre(ligaGuardada.getNombre());
        response.setPais(ligaGuardada.getPais());
        response.setLogoUrl(ligaGuardada.getLogoUrl());

        return response;
    }

    public DtoLigasResponse actualizarLiga(Long id, DtoLigasRequest requestDto){
        log.info("Actualizando liga con ID: {}", id);
        
        LigasModel ligaExistente = ligasrepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada con ID: " + id));

        ligaExistente.setNombre(requestDto.getNombre());
        ligaExistente.setPais(requestDto.getPais());
        ligaExistente.setLogoUrl(requestDto.getLogoUrl());

        LigasModel ligaActualizada = ligasrepository.save(ligaExistente);

        DtoLigasResponse response = new DtoLigasResponse();
        response.setId(ligaActualizada.getId());
        response.setNombre(ligaActualizada.getNombre());
        response.setPais(ligaActualizada.getPais());
        response.setLogoUrl(ligaActualizada.getLogoUrl());

        return response;
    }

    public void eliminarLiga(Long id){
        log.info("Eliminando liga con ID: {}", id);
        
        LigasModel ligaExistente = ligasrepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Liga no encontrada con ID: " + id));
                
        ligasrepository.delete(ligaExistente);
    }
}
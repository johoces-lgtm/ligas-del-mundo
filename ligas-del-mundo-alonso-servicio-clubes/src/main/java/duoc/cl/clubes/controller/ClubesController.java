package duoc.cl.clubes.controller;

import duoc.cl.clubes.dto.request.DtoClubesRequest;
import duoc.cl.clubes.dto.response.DtoClubesResponse;
import duoc.cl.clubes.service.ClubesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubes")
@Slf4j
public class ClubesController {

    @Autowired
    private ClubesService clubesService;

    @GetMapping
    public ResponseEntity<List<DtoClubesResponse>> listarTodos() {
        log.info("GET /api/clubes - Solicitando lista completa");
        return ResponseEntity.ok(clubesService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoClubesResponse> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/clubes/{} - Buscando club específico", id);
        return ResponseEntity.ok(clubesService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DtoClubesResponse> guardar(@Valid @RequestBody DtoClubesRequest request) {
        log.info("POST /api/clubes - Registrando nuevo club: {}", request.getNombre());
        return new ResponseEntity<>(clubesService.crear(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/clubes/{} - Eliminando club", id);
        clubesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
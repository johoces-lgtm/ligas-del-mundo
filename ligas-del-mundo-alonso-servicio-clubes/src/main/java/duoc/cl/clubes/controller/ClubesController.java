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
@Tag(name = "Clubes", description = "Endpoints para la gestión completa de Clubes de fútbol")
public class ClubesController {

    @Autowired
    private ClubesService clubesService;

    @Operation(summary = "Listar todos los clubes", description = "Retorna una lista con todos los clubes registrados actualmente.")
    @GetMapping
    public ResponseEntity<List<DtoClubesResponse>> listarTodos() {
        log.info("GET /api/clubes - Solicitando lista completa");
        return ResponseEntity.ok(clubesService.listarTodos());
    }

    @Operation(summary = "Obtener un club por ID", description = "Busca y retorna los detalles exactos de un club en base a su identificador único.")
    @GetMapping("/{id}")
    public ResponseEntity<DtoClubesResponse> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/clubes/{} - Buscando club especifico", id);
        return ResponseEntity.ok(clubesService.buscarPorId(id));
    }

    @Operation(summary = "Registrar un nuevo club", description = "Crea un club en el sistema validando previamente que el ID del país asociado exista.")
    @PostMapping
    public ResponseEntity<DtoClubesResponse> guardar(@Valid @RequestBody DtoClubesRequest request) {
        log.info("POST /api/clubes - Registrando nuevo club: {}", request.getNombre());
        return new ResponseEntity<>(clubesService.crear(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar un club", description = "Borra físicamente el registro de un club del sistema mediante su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/clubes/{} - Eliminando club", id);
        clubesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
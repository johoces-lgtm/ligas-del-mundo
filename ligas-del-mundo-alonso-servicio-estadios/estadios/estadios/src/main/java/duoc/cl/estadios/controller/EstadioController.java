package duoc.cl.estadios.controller;

import duoc.cl.estadios.dto.response.DtoEstadioResponse;
import duoc.cl.estadios.dto.request.DtoEstadioRequest;
import duoc.cl.estadios.service.EstadioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estadios")
@RequiredArgsConstructor
@Slf4j // Activamos los logs
@Tag(name = "Estadios", description = "API para gestionar los estadios de los clubes")
public class EstadioController {

    private final EstadioService estadioService;

    @PostMapping
    @Operation(summary = "Registrar un estadio", description = "Crea un nuevo registro de un estadio en la base de datos")
    public ResponseEntity<DtoResponseEstadios> crear(@Valid @RequestBody DtoEstadiosRequest dto) {
        log.info("POST /api/estadios - Registrando nuevo estadio: {}", dto.getNombre());
        return new ResponseEntity<>(estadioService.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar estadios", description = "Obtiene una lista con todos los estadios registrados")
    public ResponseEntity<List<DtoResponseEstadios>> listar() {
        log.info("GET /api/estadios - Solicitando lista completa de estadios");
        return ResponseEntity.ok(estadioService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Busca un estadio específico mediante su identificador único")
    public ResponseEntity<DtoResponseEstadios> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/estadios/{} - Buscando estadio específico", id);
        return ResponseEntity.ok(estadioService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estadio", description = "Elimina un estadio de la base de datos usando su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/estadios/{} - Eliminando estadio", id);
        estadioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
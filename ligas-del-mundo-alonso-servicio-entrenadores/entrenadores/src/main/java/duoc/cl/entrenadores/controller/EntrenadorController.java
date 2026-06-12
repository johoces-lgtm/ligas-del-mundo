package duoc.cl.entrenadores.controller;


import duoc.cl.entrenadores.dto.request.DtoEntrenadorRequest;
import duoc.cl.entrenadores.dto.response.DtoControllerEntrenadores;
import duoc.cl.entrenadores.service.EntrenadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
@RequiredArgsConstructor
@Slf4j // Activamos los logs
@Tag(name = "Entrenadores", description = "API para gestionar los directores técnicos del sistema")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    @PostMapping
    @Operation(summary = "Registrar un entrenador", description = "Crea un nuevo registro de un entrenador en la base de datos")
    public ResponseEntity<DtoResponseEntrenadores> crear(@Valid @RequestBody DtoEntrenadorRequest dto) {
        log.info("POST /api/entrenadores - Registrando nuevo entrenador: {}", dto.getNombre());
        return new ResponseEntity<>(entrenadorService.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar entrenadores", description = "Obtiene una lista con todos los entrenadores registrados")
    public ResponseEntity<List<DtoResponseEntrenadores>> listar() {
        log.info("GET /api/entrenadores - Solicitando lista completa de entrenadores");
        return ResponseEntity.ok(entrenadorService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Busca un entrenador específico mediante su identificador único")
    public ResponseEntity<DtoResponseEntrenadores> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/entrenadores/{} - Buscando entrenador específico", id);
        return ResponseEntity.ok(entrenadorService.buscarPorId(id));
    }
}
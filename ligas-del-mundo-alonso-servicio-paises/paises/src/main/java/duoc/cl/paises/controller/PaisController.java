package duoc.cl.paises.controller;

import duoc.cl.paises.dto.request.DtoPaisRequest;
import duoc.cl.paises.dto.response.DtoPaisResponse;
import duoc.cl.paises.service.PaisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/paises")
@RequiredArgsConstructor
@Slf4j // Activamos los logs
@Tag(name = "Países", description = "API para gestionar los países del sistema")
public class PaisController {

    private final PaisService paisService;

    @PostMapping
    @Operation(summary = "Registrar un país", description = "Crea un nuevo registro de un país en la base de datos")
    public ResponseEntity<DtoResponsePaises> crear(@Valid @RequestBody DtoPaisesRequest dto) {
        log.info("POST /api/paises - Registrando nuevo país: {}", dto.getNombre());
        return new ResponseEntity<>(paisService.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar países", description = "Obtiene una lista con todos los países registrados")
    public ResponseEntity<List<DtoResponsePaises>> listar() {
        log.info("GET /api/paises - Solicitando lista completa de países");
        return ResponseEntity.ok(paisService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Busca un país específico mediante su identificador único")
    public ResponseEntity<DtoResponsePaises> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/paises/{} - Buscando país específico", id);
        return ResponseEntity.ok(paisService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar país", description = "Elimina un país de la base de datos usando su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/paises/{} - Eliminando país", id);
        paisService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
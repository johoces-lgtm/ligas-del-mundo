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
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    @PostMapping
    public ResponseEntity<DtoControllerEntrenadores> crear(@Valid @RequestBody DtoEntrenadorRequest dto) {
        return new ResponseEntity<>(entrenadorService.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DtoControllerEntrenadores>> listar() {
        return ResponseEntity.ok(entrenadorService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoControllerEntrenadores> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(entrenadorService.buscarPorId(id));
    }
}
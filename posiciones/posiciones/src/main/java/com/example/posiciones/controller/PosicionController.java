package com.example.posiciones.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.posiciones.dto.request.PosicionRequestDto;
import com.example.posiciones.dto.response.PartidoResponseDto;
import com.example.posiciones.model.Posicion;
import com.example.posiciones.service.PosicionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posiciones")
@CrossOrigin("*")
public class PosicionController {

    private final PosicionService service;

    public PosicionController(PosicionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Posicion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Posicion> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<Posicion> guardar(@Valid @RequestBody PosicionRequestDto dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Posicion> actualizar(@PathVariable Long id, @Valid @RequestBody PosicionRequestDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/partidos")
    public ResponseEntity<List<PartidoResponseDto>> listarPartidosConsumidos() {
        return ResponseEntity.ok(service.obtenerPartidos());
    }
}
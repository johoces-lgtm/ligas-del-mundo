package cl.duoc.lesiones.controller;

import cl.duoc.lesiones.dto.request.DtoLesionesRequest;
import cl.duoc.lesiones.dto.response.DtoLesionesResponse;
import cl.duoc.lesiones.service.LesionesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesiones")
public class LesionesController {

    @Autowired
    private LesionesService service;

    @GetMapping
    public ResponseEntity<List<DtoLesionesResponse>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<DtoLesionesResponse>> obtenerPorJugador(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(service.obtenerPorJugador(jugadorId));
    }

    @PostMapping
    public ResponseEntity<DtoLesionesResponse> guardar(@Valid @RequestBody DtoLesionesRequest request) {
        return new ResponseEntity<>(service.guardar(request), HttpStatus.CREATED);
    }
}
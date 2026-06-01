package cl.duoc.jugadores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.jugadores.dto.request.DtoJugadoresRequest;
import cl.duoc.jugadores.dto.response.DtoJugadoresResponse;
import cl.duoc.jugadores.service.JugadoresService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jugadores")
public class JugadoresController {

    @Autowired
    private JugadoresService service;

    @GetMapping
    public ResponseEntity<List<DtoJugadoresResponse>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoJugadoresResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<DtoJugadoresResponse>> obtenerPorClub(@PathVariable Long clubId) {
        return ResponseEntity.ok(service.obtenerPorClub(clubId));
    }

    @PostMapping
    public ResponseEntity<DtoJugadoresResponse> guardar(@Valid @RequestBody DtoJugadoresRequest request) {
        return new ResponseEntity<>(service.guardar(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}

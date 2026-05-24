package com.example.partidos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.partidos.dto.PartidoRequestDto;
import com.example.partidos.model.Partido;
import com.example.partidos.service.PartidoService;

@RestController
@RequestMapping("/api/partidos")
@CrossOrigin("*")
public class PartidoController {

    private final PartidoService service;

    public PartidoController(PartidoService service) {

        this.service = service;
    }

    @GetMapping
    public List<Partido> listar() {

        return service.listar();
    }

    @GetMapping("/{id}")
    public Partido buscar(@PathVariable Long id) {

        return service.buscar(id);
    }

    @PostMapping
    public Partido guardar(@RequestBody PartidoRequestDto dto) {

        return service.guardar(dto);
    }

    @PutMapping("/{id}")
    public Partido actualizar(
            @PathVariable Long id,
            @RequestBody PartidoRequestDto dto) {

        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {

        service.eliminar(id);
    }
}
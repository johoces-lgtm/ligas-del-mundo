package com.example.posiciones.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.posiciones.dto.PosicionRequestDto;
import com.example.posiciones.model.Posicion;
import com.example.posiciones.service.PosicionService;

@RestController
@RequestMapping("/posiciones")
@CrossOrigin("*")
public class PosicionController {

    private final PosicionService service;

    public PosicionController(PosicionService service) {

        this.service = service;
    }

    @GetMapping
    public List<Posicion> listar() {

        return service.listar();
    }

    @GetMapping("/{id}")
    public Posicion buscar(@PathVariable Long id) {

        return service.buscar(id);
    }

    @PostMapping
    public Posicion guardar(@RequestBody PosicionRequestDto dto) {

        return service.guardar(dto);
    }

    @PutMapping("/{id}")
    public Posicion actualizar(
            @PathVariable Long id,
            @RequestBody PosicionRequestDto dto) {

        return service.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {

        service.eliminar(id);
    }
}
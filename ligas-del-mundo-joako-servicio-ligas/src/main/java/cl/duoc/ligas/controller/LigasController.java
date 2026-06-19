package cl.duoc.ligas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.ligas.dto.response.DtoLigasResponse;
import cl.duoc.ligas.service.LigasService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ligas")
public class LigasController {

    @Autowired
    private LigasService ligasService;

    @GetMapping
    public ResponseEntity<List<DtoLigasResponse>> listarLigas(){
        return new ResponseEntity<>(ligasService.obtenerTodasLasLigas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DtoLigasResponse> obtenerLigaPorId(@PathVariable Long id){
        return new ResponseEntity<>(ligasService.obtenerLigaPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DtoLigasResponse> guardarLiga(@Valid @RequestBody DtoLigasResponse requestDto){
        return new ResponseEntity<>(ligasService.crearLiga(requestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DtoLigasResponse> actualizarLiga(@PathVariable Long id, @Valid @RequestBody DtoLigasResponse requestDto){
        return new ResponseEntity<>(ligasService.actualizarLiga(id, requestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarLiga(@PathVariable Long id){
        ligasService.eliminarLiga(id);
        return new ResponseEntity<>("Liga eliminada exitosamente", HttpStatus.OK);
    }

}

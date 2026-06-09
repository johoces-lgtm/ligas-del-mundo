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

import cl.duoc.ligas.dto.request.DtoLigasRequest;
import cl.duoc.ligas.dto.response.DtoLigasResponse;
import cl.duoc.ligas.service.LigasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ligas")
@Tag(name = "Ligas", description = "Operaciones CRUD para el manejo de las Ligas del Mundo") 
public class LigasController {

    @Autowired
    private LigasService ligasService;

    @GetMapping
    @Operation(summary = "Listar todas las ligas", description = "Retorna una lista completa de las ligas almacenadas en BD local")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<DtoLigasResponse>> listarLigas(){
        return new ResponseEntity<>(ligasService.obtenerTodasLasLigas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener liga por ID", description = "Busca y retorna los detalles de una liga específica mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liga encontrada exitosamente"),
        @ApiResponse(responseCode = "404", description = "La liga no existe", content = @Content)
    })
    public ResponseEntity<DtoLigasResponse> obtenerLigaPorId(@PathVariable Long id){
        return new ResponseEntity<>(ligasService.obtenerLigaPorId(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Registrar nueva liga", description = "Guarda una nueva liga validando los datos de entrada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Liga creada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoLigasResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content)
    })
    public ResponseEntity<DtoLigasResponse> guardarLiga(@Valid @RequestBody DtoLigasRequest requestDto){
        return new ResponseEntity<>(ligasService.crearLiga(requestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar liga", description = "Actualiza los datos de una liga específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liga actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoLigasResponse.class))),
        @ApiResponse(responseCode = "404", description = "La liga no existe", content = @Content)
    })
    public ResponseEntity<DtoLigasResponse> actualizarLiga(@PathVariable Long id, @Valid @RequestBody DtoLigasRequest requestDto){
        return new ResponseEntity<>(ligasService.actualizarLiga(id, requestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar liga", description = "Elimina una liga específica por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liga eliminada exitosamente", content = @Content (mediaType = "text/plain")),
        @ApiResponse(responseCode = "404", description = "La liga no existe", content = @Content)
    })
    public ResponseEntity<String> eliminarLiga(@PathVariable Long id){
        ligasService.eliminarLiga(id);
        return new ResponseEntity<>("Liga eliminada exitosamente", HttpStatus.OK);
    }

}

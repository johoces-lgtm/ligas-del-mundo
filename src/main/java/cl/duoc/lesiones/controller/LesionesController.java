package cl.duoc.lesiones.controller;

import cl.duoc.lesiones.dto.request.DtoLesionesRequest;
import cl.duoc.lesiones.dto.response.DtoLesionesResponse;
import cl.duoc.lesiones.service.LesionesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesiones")
@Tag(name = "Lesiones", description = "Endpoints de control clínico e historial de lesiones médicas para los deportistas")
public class LesionesController {

    @Autowired
    private LesionesService service;

    @GetMapping
    @Operation(summary = "Listar el histórico total de lesiones", description = "Retorna el consolidado absoluto de incidentes médicos almacenados")
    @ApiResponse(responseCode = "200", description = "Historial médico recuperado con éxito")
    public ResponseEntity<List<DtoLesionesResponse>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/jugador/{jugadorId}")
    @Operation(summary = "Obtener historial de lesiones de un jugador específico", description = "Filtra y devuelve todos los registros médicos correspondientes a un JugadorID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial médico del deportista procesado correctamente"),
        @ApiResponse(responseCode = "404", description = "El ID del jugador ingresado no existe en el ecosistema", content = @Content)
    })
    public ResponseEntity<List<DtoLesionesResponse>> obtenerPorJugador(@PathVariable Long jugadorId) {
        return ResponseEntity.ok(service.obtenerPorJugador(jugadorId));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo informe de lesión médica", description = "Inserta un parte médico verificando de forma síncrona que el jugador afectado exista en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Lesión reportada y guardada con éxito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoLesionesResponse.class))),
        @ApiResponse(responseCode = "404", description = "Imposible asociar lesión: El ID del futbolista no existe", content = @Content),
        @ApiResponse(responseCode = "400", description = "Fallo de validación estructural en el formato JSON", content = @Content)
    })
    public ResponseEntity<DtoLesionesResponse> guardar(@Valid @RequestBody DtoLesionesRequest request) {
        return new ResponseEntity<>(service.guardar(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un parte médico existente", description = "Modifica un registro de lesión ya guardado, permitiendo corregir o completar información previa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lesión actualizada exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoLesionesResponse.class))),
        @ApiResponse(responseCode = "404", description = "El ID de la lesión no existe", content = @Content)
    })
    public ResponseEntity<DtoLesionesResponse> actualizar(@PathVariable Long id, @Valid @RequestBody DtoLesionesRequest request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }
}
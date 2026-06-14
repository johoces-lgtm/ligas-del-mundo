package com.example.partidos.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.partidos.dto.request.PartidoRequestDto;
import com.example.partidos.model.Partido;
import com.example.partidos.service.PartidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/partidos")
@CrossOrigin("*")
@Tag(name = "Controlador de Partidos", description = "Endpoints para la programación y resultados de encuentros deportivos")
@SecurityRequirement(name = "bearerAuth")
public class PartidoController {

    private final PartidoService service;

    public PartidoController(PartidoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar partidos", description = "Retorna el historial de partidos. Lanza pings de verificación a los servicios externos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de encuentros obtenida con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "503", description = "Servicio externo (Ligas, Clubes o Estadios) inalcanzable")
    })
    public ResponseEntity<List<Partido>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar partido por ID", description = "Obtiene los detalles de un partido validando consistencia de datos maestros.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Partido encontrado de manera exitosa"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "409", description = "Inconsistencia: Los datos del partido hacen referencia a entidades externas eliminadas")
    })
    public ResponseEntity<Partido> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Programar un nuevo partido", description = "Crea un registro de partido verificando que existan sus dependencias en los otros microservicios.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Partido creado correctamente"),
        @ApiResponse(responseCode = "400", description = "JSON con datos inválidos o faltantes"),
        @ApiResponse(responseCode = "401", description = "No autorizado")
    })
    public ResponseEntity<Partido> guardar(@Valid @RequestBody PartidoRequestDto dto) {
        return new ResponseEntity<>(service.guardar(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un partido", description = "Modifica goles, estados o IDs de un encuentro en juego o finalizado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Encuentro actualizado con éxito"),
        @ApiResponse(responseCode = "400", description = "Estructura del request incorrecta"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "El ID del partido solicitado no existe")
    })
    public ResponseEntity<Partido> actualizar(@PathVariable Long id, @Valid @RequestBody PartidoRequestDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un partido", description = "Elimina permanentemente el registro del partido de la base de datos.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Partido eliminado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Partido no encontrado")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
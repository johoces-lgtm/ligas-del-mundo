package com.example.posiciones.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.posiciones.dto.request.PosicionRequestDto;
import com.example.posiciones.dto.response.PartidoResponseDto;
import com.example.posiciones.model.Posicion;
import com.example.posiciones.service.PosicionService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/posiciones")
@CrossOrigin("*")
@Tag(name = "Controlador de Posiciones", description = "Endpoints para gestionar la tabla de posiciones y comunicación externa")
@SecurityRequirement(name = "bearerAuth") // Aplica el candado JWT
public class PosicionController {

    private final PosicionService service;

    public PosicionController(PosicionService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar posiciones", description = "Obtiene la tabla de posiciones completa. Hace ping al microservicio de partidos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tabla de posiciones obtenida con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "503", description = "Servicio de Partidos apagado o no disponible")
    })
    public ResponseEntity<List<Posicion>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar posición por ID", description = "Busca un registro de posición específico en la base de datos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado con éxito"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "Registro de posición no encontrado")
    })
    public ResponseEntity<Posicion> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Guardar nueva posición", description = "Registra una posición validando que el club exista en el microservicio externo de Clubes.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Posición guardada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación en el cuerpo del JSON"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "El Club ingresado no existe en el microservicio externo")
    })
    public ResponseEntity<Posicion> guardar(@Valid @RequestBody PosicionRequestDto dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar posición existente", description = "Modifica los datos de rendimiento de un club por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Posición actualizada con éxito"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "No se encontró el registro o el Club externo")
    })
    public ResponseEntity<Posicion> actualizar(@PathVariable Long id, @Valid @RequestBody PosicionRequestDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar registro de posición", description = "Remueve permanentemente el registro por su ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "24", description = "Eliminado exitosamente"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "404", description = "El registro no existe")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/partidos")
    @Operation(summary = "Listar partidos consumidos", description = "Consume mediante WebClient el listado de partidos del microservicio externo.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de partidos obtenidos de forma externa"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "503", description = "El microservicio de partidos no respondió")
    })
    public ResponseEntity<List<PartidoResponseDto>> listarPartidosConsumidos() {
        return ResponseEntity.ok(service.obtenerPartidos());
    }
}
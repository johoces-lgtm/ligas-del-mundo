package duoc.cl.estadios.controller;

import duoc.cl.estadios.dto.response.DtoEstadioResponse;
import duoc.cl.estadios.dto.request.DtoEstadioRequest;
import duoc.cl.estadios.service.EstadioService;
import duoc.cl.estadios.dto.DtoApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/estadios")
@RequiredArgsConstructor
@Slf4j // Activamos los logs
@Tag(name = "Estadios", description = "API para gestionar los estadios de los clubes")
public class EstadioController {

    private final EstadioService estadioService;

    @PostMapping
    @Operation(summary = "Registrar un estadio", description = "Crea un nuevo registro de un estadio en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Estadio creado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoEstadioResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el estadio",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<DtoEstadioResponse> crear(@Valid @RequestBody DtoEstadioRequest dto) {
        log.info("POST /api/estadios - Registrando nuevo estadio: {}", dto.getNombre());
        return new ResponseEntity<>(estadioService.crearEstadio(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar estadios", description = "Obtiene una lista con todos los estadios registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de estadios obtenida correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoEstadioResponse.class))),
        @ApiResponse(responseCode = "204", description = "No hay estadios registrados",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener la lista de estadios",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<List<DtoEstadioResponse>> listar() {
        log.info("GET /api/estadios - Solicitando lista completa de estadios");
        return ResponseEntity.ok(estadioService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Busca un estadio específico mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadio encontrado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoEstadioResponse.class))),
        @ApiResponse(responseCode = "404", description = "Estadio no encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al buscar el estadio",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<DtoEstadioResponse> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/estadios/{} - Buscando estadio específico", id);
        return ResponseEntity.ok(estadioService.obtenerEstadioPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar estadio", description = "Elimina un estadio de la base de datos usando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estadio eliminado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "404", description = "Estadio no encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el estadio",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/estadios/{} - Eliminando estadio", id);
        estadioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
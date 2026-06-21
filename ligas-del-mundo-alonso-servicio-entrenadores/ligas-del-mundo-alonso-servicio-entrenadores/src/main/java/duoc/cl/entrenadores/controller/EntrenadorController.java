package duoc.cl.entrenadores.controller;

import duoc.cl.entrenadores.dto.request.DtoEntrenadorRequest;
import duoc.cl.entrenadores.dto.response.DtoEntrenadorResponse;
import duoc.cl.entrenadores.service.EntrenadorService;
import duoc.cl.entrenadores.dto.DtoApiError;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/entrenadores")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Entrenadores", description = "API para gestionar los directores tecnicos del sistema")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    @PostMapping
    @Operation(summary = "Registrar un entrenador", description = "Crea un nuevo registro de un entrenador validando externamente que el club exista.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Entrenador creado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoEntrenadorResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud invalida por errores de validacion",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "404", description = "El Club referenciado no existe",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<DtoEntrenadorResponse> crear(@Valid @RequestBody DtoEntrenadorRequest dto) {
        log.info("POST /api/entrenadores - Registrando nuevo entrenador: {}", dto.getNombre());
        return new ResponseEntity<>(entrenadorService.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar entrenadores", description = "Obtiene una lista con todos los entrenadores registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de entrenadores obtenida correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoEntrenadorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<List<DtoEntrenadorResponse>> listar() {
        log.info("GET /api/entrenadores - Solicitando lista completa de entrenadores");
        return ResponseEntity.ok(entrenadorService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Busca un entrenador especifico mediante su identificador unico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Entrenador encontrado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoEntrenadorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Entrenador no encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<DtoEntrenadorResponse> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/entrenadores/{} - Buscando entrenador especifico", id);
        return ResponseEntity.ok(entrenadorService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar entrenador", description = "Elimina un entrenador de la base de datos usando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Entrenador eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Entrenador no encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/entrenadores/{} - Eliminando entrenador", id);
        entrenadorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
package duoc.cl.clubes.controller;

import duoc.cl.clubes.dto.request.DtoClubesRequest;
import duoc.cl.clubes.dto.response.DtoClubesResponse;
import duoc.cl.clubes.dto.DtoApiError;
import duoc.cl.clubes.service.ClubesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/clubes")
@Slf4j
@Tag(name = "Clubes", description = "Endpoints para la gestión completa de clubes de fútbol")
public class ClubesController {

    @Autowired
    private ClubesService clubesService;

    @Operation(summary = "Obtener todos los clubes", description = "Recupera una lista con todos los clubes registrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clubes obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoClubesResponse.class))),
        @ApiResponse(responseCode = "204", description = "No hay clubes registrados"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @GetMapping
    public ResponseEntity<List<DtoClubesResponse>> listarTodos() {
        log.info("GET /api/clubes - Solicitando lista completa");
        return ResponseEntity.ok(clubesService.listarTodos());
    }

    @Operation(summary = "Obtener club por ID", description = "Recupera un club específico mediante su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Club obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoClubesResponse.class))),
        @ApiResponse(responseCode = "404", description = "Club no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DtoClubesResponse> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/clubes/{} - Buscando club específico", id);
        return ResponseEntity.ok(clubesService.buscarPorId(id));
    }

    @Operation(summary = "Registrar un nuevo club", description = "Crea un nuevo club en el sistema validando previamente que el ID de la liga asociada exista")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Club creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoClubesResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "404", description = "Liga no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @PostMapping
    public ResponseEntity<DtoClubesResponse> guardar(@Valid @RequestBody DtoClubesRequest request) {
        log.info("POST /api/clubes - Registrando nuevo club: {}", request.getNombre());
        return new ResponseEntity<>(clubesService.crear(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar un club", description = "Borra físicamente el registro de un club del sistema mediante su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Club eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Club no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/clubes/{} - Eliminando club", id);
        clubesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
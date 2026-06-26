package duoc.cl.clubes.controller;

import duoc.cl.clubes.dto.request.DtoClubesRequest;
import duoc.cl.clubes.dto.response.DtoClubesResponse;
import duoc.cl.clubes.service.ClubesService;
import duoc.cl.clubes.dto.DtoApiError;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/clubes")
@Slf4j
@Tag(name = "Clubes", description = "Endpoints para la gestión completa de Clubes de fútbol")
public class ClubesController {

    @Autowired
    private ClubesService clubesService;

    @Operation(summary = "Listar todos los clubes", description = "Retorna una lista con todos los clubes registrados actualmente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clubes obtenida correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoClubesResponse.class))),
        @ApiResponse(responseCode = "204", description = "No hay clubes registrados",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener la lista de clubes",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @GetMapping
    public ResponseEntity<List<DtoClubesResponse>> listarTodos() {
        log.info("GET /api/clubes - Solicitando lista completa");
        return ResponseEntity.ok(clubesService.listarTodos());
    }

    @Operation(summary = "Obtener un club por ID", description = "Busca y retorna los detalles exactos de un club en base a su identificador único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Club encontrado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoClubesResponse.class))),
        @ApiResponse(responseCode = "404", description = "Club no encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener el club",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DtoClubesResponse> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/clubes/{} - Buscando club especifico", id);
        return ResponseEntity.ok(clubesService.buscarPorId(id));
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Club creado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoClubesResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "404", description = "La Liga asociada no existe", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el club",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @PostMapping
    public ResponseEntity<DtoClubesResponse> guardar(@Valid @RequestBody DtoClubesRequest request) {
        log.info("POST /api/clubes - Registrando nuevo club: {}", request.getNombre());
        return new ResponseEntity<>(clubesService.crear(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar un club", description = "Borra físicamente el registro de un club del sistema mediante su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Club eliminado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "404", description = "Club no encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el club",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/clubes/{} - Eliminando club", id);
        clubesService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
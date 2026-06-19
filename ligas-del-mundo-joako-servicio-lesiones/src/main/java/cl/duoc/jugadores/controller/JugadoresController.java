package cl.duoc.jugadores.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.jugadores.dto.request.DtoJugadoresRequest;
import cl.duoc.jugadores.dto.response.DtoJugadoresResponse;
import cl.duoc.jugadores.service.JugadoresService;
import cl.duoc.jugadores.dto.DtoApiError;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/jugadores")
@Tag(name = "Jugadores", description = "Endpoints para la gestión completa de jugadores de fútbol")
public class JugadoresController {

    @Autowired
    private JugadoresService service;

    @Operation(summary = "Obtener todos los jugadores", description = "Recupera una lista con todos los jugadores registrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de jugadores obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoJugadoresResponse.class))),
        @ApiResponse(responseCode = "204", description = "No hay jugadores registrados"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @GetMapping
    public ResponseEntity<List<DtoJugadoresResponse>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @Operation(summary = "Obtener jugador por ID", description = "Recupera un jugador específico mediante su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Jugador obtenido correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoJugadoresResponse.class))),
        @ApiResponse(responseCode = "404", description = "Jugador no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DtoJugadoresResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Obtener jugadores por club", description = "Recupera una lista de jugadores asociados a un club específico mediante el ID del club")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de jugadores obtenida correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoJugadoresResponse.class))),
        @ApiResponse(responseCode = "404", description = "Club no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<DtoJugadoresResponse>> obtenerPorClub(@PathVariable Long clubId) {
        return ResponseEntity.ok(service.obtenerPorClub(clubId));
    }

    @Operation(summary = "Registrar un nuevo jugador", description = "Crea un nuevo jugador en el sistema validando previamente que el ID del club asociado exista")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Jugador creado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoJugadoresResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "404", description = "Club no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @PostMapping
    public ResponseEntity<DtoJugadoresResponse> guardar(@Valid @RequestBody DtoJugadoresRequest request) {
        return new ResponseEntity<>(service.guardar(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar un jugador", description = "Borra físicamente el registro de un jugador del sistema mediante su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Jugador eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Jugador no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}

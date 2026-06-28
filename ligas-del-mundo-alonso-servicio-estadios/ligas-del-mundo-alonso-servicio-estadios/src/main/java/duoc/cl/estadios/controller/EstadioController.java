package duoc.cl.estadios.controller;

import duoc.cl.estadios.dto.request.DtoEstadioRequest;
import duoc.cl.estadios.dto.response.DtoEstadioResponse;
import duoc.cl.estadios.dto.DtoApiError;
import duoc.cl.estadios.service.EstadioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@RequestMapping("/api/estadios")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Estadios", description = "Endpoints para la administracion de estadios deportivos con validacion externa")
public class EstadioController {

    private final EstadioService estadioService;

    @Operation(summary = "Crear nuevo estadio", description = "Inserta un nuevo estadio en el sistema validando sincronamente que el ID de pais exista en el microservicio externo.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Estadio guardado con exito", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoEstadioResponse.class))),
        @ApiResponse(responseCode = "400", description = "Atributos invalidos provistos en el Request Body", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "404", description = "Fallo de integridad: El ID de pais no fue encontrado externamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno no controlado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @PostMapping
    public ResponseEntity<DtoEstadioResponse> crear(@Valid @RequestBody DtoEstadioRequest dto) {
        log.info("POST /api/estadios - Registrando nuevo estadio: {}", dto.getNombre());
        return new ResponseEntity<>(estadioService.crearEstadio(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener estadio por ID", description = "Busca el registro de un estadio por su ID unico.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estadio retornado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoEstadioResponse.class))),
        @ApiResponse(responseCode = "404", description = "Estadio no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
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

    @Operation(summary = "Listar todos los estadios", description = "Recupera una coleccion completa de todos los estadios dados de alta.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Coleccion cargada correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoEstadioResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error del sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @GetMapping
    public List<DtoEstadioResponse> listarTodos() {
        return estadioService.obtenerTodos();
    }
}
package duoc.cl.paises.controller;

import duoc.cl.paises.dto.request.DtoPaisRequest;
import duoc.cl.paises.dto.response.DtoPaisResponse;
import duoc.cl.paises.service.PaisService;
import duoc.cl.paises.dto.DtoApiError;
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
@RequestMapping("/api/paises")
@RequiredArgsConstructor
@Slf4j // Activamos los logs
@Tag(name = "Países", description = "API para gestionar los países del sistema")
public class PaisController {

    private final PaisService paisService;

    @PostMapping
    @Operation(summary = "Registrar un país", description = "Crea un nuevo registro de un país en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "País creado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoPaisResponse.class))),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el país",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<DtoPaisResponse> crear(@Valid @RequestBody DtoPaisRequest dto) {
        log.info("POST /api/paises - Registrando nuevo país: {}", dto.getNombre());
        return new ResponseEntity<>(paisService.crear(dto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar países", description = "Obtiene una lista con todos los países registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de países obtenida correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoPaisResponse.class))),
        @ApiResponse(responseCode = "204", description = "No hay países registrados",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener la lista de países",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<List<DtoPaisResponse>> listar() {
        log.info("GET /api/paises - Solicitando lista completa de países");
        return ResponseEntity.ok(paisService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Busca un país específico mediante su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País encontrado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoPaisResponse.class))),
        @ApiResponse(responseCode = "404", description = "País no encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al buscar el país",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<DtoPaisResponse> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/paises/{} - Buscando país específico", id);
        return ResponseEntity.ok(paisService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar país", description = "Elimina un país de la base de datos usando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "País eliminado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "404", description = "País no encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el país",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/paises/{} - Eliminando país", id);
        paisService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
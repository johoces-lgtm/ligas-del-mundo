package duoc.cl.paises.controller;

import duoc.cl.paises.dto.request.DtoPaisRequest;
import duoc.cl.paises.dto.response.DtoPaisResponse;
import duoc.cl.paises.dto.DtoApiError;
import duoc.cl.paises.service.PaisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/paises")
@RequiredArgsConstructor
@Tag(name = "Paises", description = "Endpoints para la gestion y consulta de paises de las competiciones")
public class PaisController {

    private final PaisService paisService;

    @Operation(summary = "Registrar nuevo pais", description = "Registra o guarda un territorio dentro de la persistencia validando su data de entrada.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pais creado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoPaisResponse.class))),
        @ApiResponse(responseCode = "400", description = "Peticion incorrecta o fallas de validacion de campos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno de ejecucion en servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DtoPaisResponse crear(@RequestBody @Valid DtoPaisRequest request) {
        return paisService.crearPais(request);
    }

    @Operation(summary = "Obtener pais por ID", description = "Busca en la base de datos la ficha de un pais a traves de su llave primaria.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pais encontrado y retornado de forma correcta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoPaisResponse.class))),
        @ApiResponse(responseCode = "404", description = "El ID consultado no corresponde a ningun pais", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class))),
        @ApiResponse(responseCode = "500", description = "Error interno de ejecucion en servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @GetMapping("/{id}")
    public DtoPaisResponse obtenerPorId(@PathVariable Long id) {
        return paisService.obtenerPaisPorId(id);
    }

    @Operation(summary = "Listar todos los paises", description = "Retorna una lista completa de todos los paises guardados en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado completo recuperado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoPaisResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno de ejecucion en servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    @GetMapping
    public List<DtoPaisResponse> listarTodos() {
        return paisService.obtenerTodos();
    }
}
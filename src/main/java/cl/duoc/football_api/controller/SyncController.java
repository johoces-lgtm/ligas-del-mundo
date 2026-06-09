package cl.duoc.football_api.controller;

import cl.duoc.football_api.dto.DtoApiError;
import cl.duoc.football_api.dto.response.ApiEjecucionResponse;
import cl.duoc.football_api.model.ApiModel;
import cl.duoc.football_api.service.ApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/sync")
@Tag(name = "Orquestador Maestro (Sync)", description = "Puntos de entrada críticos del Gateway Orquestador para poblar y distribuir datos desde la API externa Sports")
@ApiResponses(value = {
    @ApiResponse(responseCode = "401", description = "Acceso Denegado: Token JWT ausente, modificado o expirado", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
})
public class SyncController {

    private final ApiService apiService;

    public SyncController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/ligas")
    @Operation(summary = "Sincronizar Ligas Mundiales Principales", description = "Extrae de forma remota las ligas configuradas y las inyecta en el microservicio local de Ligas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sincronización masiva de ligas finalizada correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiEjecucionResponse.class))),
        @ApiResponse(responseCode = "500", description = "Fallo crítico en el procesamiento o timeout con la API externa", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<ApiEjecucionResponse> iniciarSincronizacionLigas() {
        log.info("POST /api/sync/ligas - Iniciando extracción...");
        ApiEjecucionResponse respuesta = apiService.procesarLigas(); 
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/logs")
    @Operation(summary = "Obtener Historial de Extracciones", description = "Recupera un listado completo de los procesos de sincronización ejecutados, incluyendo detalles como fecha, cantidad de registros procesados y resultados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Historial de extracciones recuperado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiModel.class)))
    })
    public ResponseEntity<List<ApiModel>> obtenerHistorialExtracciones() {
        log.info("GET /api/sync/logs - Solicitando historial...");
        return ResponseEntity.ok(apiService.obtenerHistorial());
    }

    @PutMapping("/logs/{id}")
    @Operation(summary = "Actualizar Observación de Registro", description = "Permite modificar la observación asociada a un registro específico del historial de extracciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Observación actualizada correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiModel.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<ApiModel> actualizarLog(@PathVariable Long id, @RequestParam String observacion) {
        log.info("PUT /api/sync/logs/{} - Actualizando observación...", id);
        ApiModel registroActualizado = apiService.actualizarObservacion(id, observacion);
        return ResponseEntity.ok(registroActualizado);
    }

    @DeleteMapping("/logs/{id}")
    @Operation(summary = "Eliminar Registro de Extracción", description = "Permite eliminar un registro específico del historial de extracciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro eliminado correctamente",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<String> eliminarLog(@PathVariable Long id) {
        log.info("DELETE /api/sync/logs/{} - Eliminando registro...", id);
        apiService.eliminarRegistro(id);
        return ResponseEntity.ok("Registro de extracción " + id + " eliminado correctamente.");
    }

    @PostMapping("/clubes")
    @Operation(summary = "Sincronizar Carga Masiva de Clubes, Directores Técnicos y Estadios", description = "Proceso maestro multi-temporada que pobla de forma paralela los microservicios locales de infraestructura deportiva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Clubes y dependencias locales/remotas mapeadas con éxito",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiEjecucionResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno en la manipulación de datos o caída de servicios remotos", 
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = DtoApiError.class)))
    })
    public ResponseEntity<ApiEjecucionResponse> iniciarSincronizacionClubes() {
        log.info("POST /api/sync/clubes - Ejecutando carga masiva de equipos...");
        ApiEjecucionResponse respuesta = apiService.procesarClubes();
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/jugadores")
    @Operation(summary = "Sincronizar Plantillas de Jugadores por Temporada", description = "Escanea la base de datos de clubes de forma local y extrae de forma remota todos los jugadores activos en la temporada indicada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Petición procesada y flujo de inyección masiva finalizado con éxito", 
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Extracción de jugadores finalizada..."))),
        @ApiResponse(responseCode = "500", description = "Fallo controlado: La API remota rechazó la petición o se agotaron las cuotas", 
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Fallo en la sincronización: ...")))
    })
    public ResponseEntity<?> sincronizarJugadores(@RequestParam(defaultValue = "2024") String temporada) {
        log.info("Recibida petición REST para sincronizar jugadores de la temporada {}", temporada);
        
        try {
            apiService.sincronizarJugadores(temporada);
            return ResponseEntity.ok("Extracción de jugadores finalizada. Revisa los logs para ver el total de procesados.");
        } catch (Exception e) {
            log.error("Error al ejecutar la sincronización de jugadores: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Fallo en la sincronización: " + e.getMessage());
        }
    }

    @PostMapping("/posiciones")
    @Operation(summary = "Sincronizar Tablas de Posiciones de las Ligas", description = "Orquesta las tablas dinámicas de clasificación deportiva para las ligas locales registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rendimiento y posiciones guardadas correctamente",
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Sincronización de posiciones finalizada con éxito."))),
        @ApiResponse(responseCode = "500", description = "Fallo al procesar o transmitir el array de posiciones hacia el servicio correspondiente",
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Error en la sincronización de posiciones.")))
    })
    public ResponseEntity<?> sincronizarPosiciones() {
        log.info("Petición para sincronizar posiciones de las 3 temporadas");
        try {
            apiService.sincronizarPosiciones();
            return ResponseEntity.ok("Sincronización de Posiciones finalizada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/partidos")
    @Operation(summary = "Sincronizar Fixtures y Calendario de Partidos", description = "Descarga la totalidad de las jornadas competitivas emparejándolas con los microservicios core correspondientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Calendario de fixtures emparejado e inyectado con éxito",
                        content = @Content(mediaType = "text/plain", schema = @Schema(example = "Sincronización de partidos finalizada..."))
        ),
        @ApiResponse(responseCode = "500", description = "Fallo crítico en el JSON externo de partidos",
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Error en la sincronización de partidos.")))
    })
    public ResponseEntity<?> sincronizarPartidos() {
        log.info("Petición para sincronizar partidos de las 3 temporadas");
        try {
            apiService.sincronizarPartidos();
            return ResponseEntity.ok("Sincronización de Partidos finalizada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/entrenadores")
    @Operation(summary = "Sincronizar Plantillas de Directores Técnicos por Temporada", description = "Escanea la base de datos de clubes de forma local y extrae de forma remota todos los directores técnicos activos en la temporada indicada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Petición procesada y flujo de inyección masiva finalizado con éxito", 
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Extracción de entrenadores finalizada..."))),
        @ApiResponse(responseCode = "500", description = "Fallo al procesar la solicitud de extracción de entrenadores",
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Error en la sincronización de entrenadores.")))
    })
    public ResponseEntity<ApiEjecucionResponse> iniciarSincronizacionEntrenadores() {
        log.info("POST /api/sync/entrenadores - Iniciando extracción masiva...");
        ApiEjecucionResponse respuesta = apiService.procesarEntrenadores();
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/estadios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estadios sincronizados con éxito",
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Sincronización de estadios finalizada..."))),
        @ApiResponse(responseCode = "500", description = "Fallo al procesar la solicitud de sincronización de estadios",
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Error en la sincronización de estadios.")))
    })
    public ResponseEntity<ApiEjecucionResponse> iniciarSincronizacionEstadios() {
        log.info("POST /api/sync/estadios - Iniciando extracción masiva...");
        ApiEjecucionResponse respuesta = apiService.sincronizarEstadios();
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/paises")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Países sincronizados con éxito",
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Sincronización de países finalizada..."))),
        @ApiResponse(responseCode = "500", description = "Fallo al procesar la solicitud de sincronización de países",
                     content = @Content(mediaType = "text/plain", schema = @Schema(example = "Error en la sincronización de países.")))
    })
    public ResponseEntity<ApiEjecucionResponse> iniciarSincronizacionPaises() {
        log.info("POST /api/sync/paises - Iniciando extracción masiva...");
        ApiEjecucionResponse respuesta = apiService.sincronizarPaises();
        return ResponseEntity.ok(respuesta);
    }
    
}
package cl.duoc.football_api.controller;

import cl.duoc.football_api.dto.response.ApiEjecucionResponse;
import cl.duoc.football_api.model.ApiModel;
import cl.duoc.football_api.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@RestController
@RequestMapping("/api/sync")
public class SyncController {

    private final ApiService apiService;

    public SyncController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/ligas")
    public ResponseEntity<ApiEjecucionResponse> iniciarSincronizacionLigas() {
        log.info("POST /api/sync/ligas - Iniciando extracción...");
        ApiEjecucionResponse respuesta = apiService.procesarLigas(); 
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/logs")
    public ResponseEntity<List<ApiModel>> obtenerHistorialExtracciones() {
        log.info("GET /api/sync/logs - Solicitando historial...");
        return ResponseEntity.ok(apiService.obtenerHistorial());
    }

    @PutMapping("/logs/{id}")
    public ResponseEntity<ApiModel> actualizarLog(@PathVariable Long id, @RequestParam String observacion) {
        log.info("PUT /api/sync/logs/{} - Actualizando observación...", id);
        ApiModel registroActualizado = apiService.actualizarObservacion(id, observacion);
        return ResponseEntity.ok(registroActualizado);
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<String> eliminarLog(@PathVariable Long id) {
        log.info("DELETE /api/sync/logs/{} - Eliminando registro...", id);
        apiService.eliminarRegistro(id);
        return ResponseEntity.ok("Registro de extracción " + id + " eliminado correctamente.");
    }

    @PostMapping("/clubes")
    public ResponseEntity<ApiEjecucionResponse> iniciarSincronizacionClubes() {
        log.info("POST /api/sync/clubes - Ejecutando carga masiva de equipos...");
        ApiEjecucionResponse respuesta = apiService.procesarClubes();
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/jugadores")
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
    public ResponseEntity<ApiEjecucionResponse> iniciarSincronizacionEntrenadores() {
        log.info("POST /api/sync/entrenadores - Iniciando extracción masiva...");
        ApiEjecucionResponse respuesta = apiService.procesarEntrenadores();
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/estadios")
    public ResponseEntity<ApiEjecucionResponse> iniciarSincronizacionEstadios() {
        log.info("POST /api/sync/estadios - Iniciando extracción masiva...");
        ApiEjecucionResponse respuesta = apiService.sincronizarEstadios();
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/paises")
    public ResponseEntity<ApiEjecucionResponse> iniciarSincronizacionPaises() {
        log.info("POST /api/sync/paises - Iniciando extracción masiva...");
        ApiEjecucionResponse respuesta = apiService.sincronizarPaises();
        return ResponseEntity.ok(respuesta);
    }
    
}
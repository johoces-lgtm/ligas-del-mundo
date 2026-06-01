package cl.duoc.football_api.service;

import cl.duoc.football_api.client.ApiFootballClient;
import cl.duoc.football_api.client.LocalClient;
import cl.duoc.football_api.dto.external.ApiClubResponse;
import cl.duoc.football_api.dto.external.ApiEntrenadorResponse;
import cl.duoc.football_api.dto.external.ApiJugadorResponse;
import cl.duoc.football_api.dto.external.ApiLeagueResponse;
import cl.duoc.football_api.dto.external.ApiPartidoResponse;
import cl.duoc.football_api.dto.external.ApiPosicionResponse;
import cl.duoc.football_api.dto.request.ClubRequest;
import cl.duoc.football_api.dto.request.EntrenadorRequest;
import cl.duoc.football_api.dto.request.EstadioRequest;
import cl.duoc.football_api.dto.request.JugadorRequest;
import cl.duoc.football_api.dto.request.LigaRequest;
import cl.duoc.football_api.dto.request.PaisRequest;
import cl.duoc.football_api.dto.request.PartidoRequest;
import cl.duoc.football_api.dto.request.PosicionRequest;
import cl.duoc.football_api.dto.response.ApiEjecucionResponse;
import cl.duoc.football_api.dto.response.ClubResponse;
import cl.duoc.football_api.model.ApiModel;
import cl.duoc.football_api.repository.ApiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ApiService {

    private final ApiFootballClient externalClient;
    private final LocalClient localClient;
    private final ApiRepository apiRepository;

    private final List<Integer> temporadasAProcesar = List.of(2022, 2023, 2024);
    private final List<Integer> ligasAProcesar = List.of(39, 140, 135, 78, 61, 253, 307);

    private final java.util.Map<Integer, Long> ligaAPaisMap = java.util.Map.of(
        39, 1L,  // Premier League -> Inglaterra (ID 1)
        140, 2L, // La Liga -> España (ID 2)
        135, 3L, // Serie A -> Italia (ID 3)
        78, 4L,  // Bundesliga -> Alemania (ID 4)
        61, 5L,  // Ligue 1 -> Francia (ID 5)
        253, 6L, // MLS -> USA (ID 6)
        307, 7L  // Pro League -> Arabia Saudita (ID 7)
    );

    public ApiService(ApiFootballClient externalClient, LocalClient localClient, ApiRepository apiRepository) {
        this.externalClient = externalClient;
        this.localClient = localClient;
        this.apiRepository = apiRepository;
    }

public ApiEjecucionResponse procesarLigas() {
        log.info("Comenzando extracción de las 7 Ligas Top...");
        int totalLigasProcesadas = 0;
        
        int temporadaFija = 2024; 

        for (Integer ligaId : ligasAProcesar) {
            try {
                ApiLeagueResponse apiResponse = externalClient.obtenerLigaPorIdYTemporada(ligaId, temporadaFija);
                if (apiResponse != null && apiResponse.getResponse() != null) {
                    for (ApiLeagueResponse.DatosResponse data : apiResponse.getResponse()) {
                        LigaRequest pedido = LigaRequest.builder()
                                .id(data.getLiga().getId())
                                .nombre(data.getLiga().getNombre())
                                .pais(data.getPais().getNombre())
                                .logoUrl(data.getLiga().getLogoUrl())
                                .build();

                        localClient.enviarLigaALocal(pedido);
                        totalLigasProcesadas++;
                    }
                }

                log.info("Pausando 5 segundos para respetar el límite de velocidad...");
                Thread.sleep(5000);
            } catch (Exception e) {
                log.error("Error en Liga {}: {}", ligaId, e.getMessage());
                registrarAuditoria("/leagues", 0, "FALLIDO: Liga " + ligaId);
            }
        }
        
        registrarAuditoria("/leagues", totalLigasProcesadas, "Extracción de Ligas completada");

        return ApiEjecucionResponse.builder()
                .mensaje("Extracción de Ligas Top completada")
                .endpoint("/leagues")
                .registrosProcesados(totalLigasProcesadas)
                .fechaEjecucion(LocalDateTime.now())
                .build();
    }

    public ApiEjecucionResponse procesarClubes() {
        log.info("Iniciando extracción masiva de Clubes para las 7 ligas...");
        int totalClubesProcesados = 0;

        for (Integer idLiga : ligasAProcesar) {
            for (Integer temporada : temporadasAProcesar) {
                try {
                    ApiClubResponse respuestaApi = externalClient.obtenerClubesPorLigaYTemporada(idLiga, temporada);

                    if (respuestaApi != null && respuestaApi.getResponse() != null) {
                        for (ApiClubResponse.DatosResponse data : respuestaApi.getResponse()) {
                        ClubRequest pedido = ClubRequest.builder()
                                .id(data.getEquipo().getId())
                                .nombre(data.getEquipo().getNombre())
                                .logoUrl(data.getEquipo().getLogoUrl())
                                .ligaId(Long.valueOf(idLiga))
                                .anioFundacion(data.getEquipo().getAnioFundacion())
                                .estadioNombre(data.getEstadio().getNombre())
                                .build();

                        localClient.enviarClubALocal(pedido);
                        totalClubesProcesados++;

                        if (data.getEstadio() != null) {
                            
                            Long idPaisDelEstadio = ligaAPaisMap.get(idLiga);

                            EstadioRequest estadioRequest = EstadioRequest.builder()
                                    .id(data.getEstadio().getId())
                                    .nombre(data.getEstadio().getNombre())
                                    .capacidad(data.getEstadio().getCapacidad())
                                    .idPais(idPaisDelEstadio) 
                                    .build();
                            localClient.enviarEstadioALocal(estadioRequest);
                        }

                        try {
                            ApiEntrenadorResponse respuestaEntrenadores = externalClient.obtenerEntrenadoresPorEquipo(data.getEquipo().getId());
                            if (respuestaEntrenadores != null && respuestaEntrenadores.getResponse() != null) {
                                for (ApiEntrenadorResponse.DatosEntrenador datos : respuestaEntrenadores.getResponse()) {
                                    EntrenadorRequest request = EntrenadorRequest.builder()
                                            .id(datos.getId())
                                            .nombre(datos.getNombre())
                                            .nacionalidad(datos.getNacionalidad())
                                            .idClub(data.getEquipo().getId())
                                            .build();
                                    localClient.enviarEntrenadorALocal(request);
                                }
                            }
                        } catch (Exception e) {
                            log.error("Error al sincronizar entrenadores: {}", e.getMessage());
                        }
                    }

                        try {
                            log.info("Pausando 7 segundos por límite de API...");
                            Thread.sleep(7000); 
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                } catch (Exception e) {
                    log.error("Fallo al traer clubes de liga {} temp {}: {}", idLiga, temporada, e.getMessage());
                    registrarAuditoria("/teams (Liga " + idLiga + ")", 0, "FALLIDO: " + e.getMessage());
                }
            }
        }

        registrarAuditoria("/teams", totalClubesProcesados, "Sincronización de clubes completada");

        return ApiEjecucionResponse.builder()
                .mensaje("Extracción de Clubes completada exitosamente")
                .endpoint("/teams")
                .registrosProcesados(totalClubesProcesados)
                .fechaEjecucion(LocalDateTime.now())
                .build();
    }

    public void sincronizarJugadores(String temporada) {
        log.info("Iniciando extracción masiva de Jugadores para la temporada {}...", temporada);

        List<ClubResponse> clubesLocales = localClient.obtenerTodosLosClubes();

        if (clubesLocales == null || clubesLocales.isEmpty()) {
            log.warn("No se encontraron clubes en la base de datos local. Abortando extracción.");
            return;
        }

        int totalProcesados = 0;

        for (ClubResponse club : clubesLocales) {
            log.info("Saliendo a internet: Buscando jugadores del club '{}' (ID: {})", club.getNombre(), club.getId());

            int paginaActual = 1;
            int totalPaginas = 1;

            do {
                try {
                    log.info("Consultando página {} de jugadores para el club {}...", paginaActual, club.getNombre());
                    ApiJugadorResponse apiResponse = externalClient.obtenerJugadoresPorEquipo(club.getId(), temporada, paginaActual);
                    
                    if (apiResponse != null) {
                        if (apiResponse.getResponse() != null) {
                            if (apiResponse.getPaging() != null && apiResponse.getPaging().getTotal() != null) {
                                totalPaginas = apiResponse.getPaging().getTotal();
                            }

                            for (ApiJugadorResponse.EntradaJugador entrada : apiResponse.getResponse()) {
                                String posicionStr = "Desconocida";
                                if (entrada.getEstadisticas() != null && !entrada.getEstadisticas().isEmpty() 
                                        && entrada.getEstadisticas().get(0).getJuegos() != null) {
                                    posicionStr = entrada.getEstadisticas().get(0).getJuegos().getPosicion();
                                }

                                JugadorRequest request = JugadorRequest.builder()
                                        .id(entrada.getJugador().getId())
                                        .nombre(entrada.getJugador().getNombre())
                                        .primerNombre(entrada.getJugador().getPrimerNombre())
                                        .apellido(entrada.getJugador().getApellido())
                                        .edad(entrada.getJugador().getEdad())
                                        .nacionalidad(entrada.getJugador().getNacionalidad())
                                        .fotoUrl(entrada.getJugador().getFotoUrl())
                                        .posicion(posicionStr)
                                        .clubId(club.getId()) 
                                        .build();

                                log.info("Enviando jugador '{}' al microservicio de Jugadores...", request.getNombre());
                                localClient.enviarJugadorALocal(request); 
                                totalProcesados++;
                            }
                        }
                        
                        if (apiResponse.getErrors() != null && !apiResponse.getErrors().toString().equals("[]") && !apiResponse.getErrors().toString().equals("{}")) {
                            log.error("La API rechazó la petición en la página {}. Motivo: {}", paginaActual, apiResponse.getErrors().toString());
                            break;
                        }
                    } else {
                        log.warn("La API no devolvió respuesta para el club '{}' en la página {}", club.getNombre(), paginaActual);
                        break;
                    }

                    paginaActual++;
                    
                    log.info("Pausando 7 segundos por límite de API...");
                    Thread.sleep(7000);

                } catch (InterruptedException e) {
                    log.error("El hilo de pausa fue interrumpido", e);
                    Thread.currentThread().interrupt();
                    return;
                } catch (Exception e) {
                    log.error("Error al procesar los jugadores del club '{}' en la página {}: {}", club.getNombre(), paginaActual, e.getMessage());
                    break;
                }
            } while (paginaActual <= totalPaginas);
        }

        log.info("Extracción de jugadores finalizada. Total procesados y enviados: {}", totalProcesados);
    }

    private void registrarAuditoria(String endpoint, int cantidad, String obs) {
        ApiModel registro = new ApiModel();
        registro.setEndpointConsultado(endpoint);
        registro.setFechaEjecucion(LocalDateTime.now());
        
        String estadoActual = obs.toUpperCase().contains("FALLO") ? "FALLIDO" : "EXITOSO";
        
        registro.setEstado(estadoActual);
        registro.setObservacion(obs);
        registro.setRegistrosProcesados(cantidad);
        apiRepository.save(registro);
    }

    public ApiEjecucionResponse procesarEntrenadores() {
        log.info("Iniciando extracción masiva de Entrenadores...");
        int totalProcesados = 0;

        try {
            List<ClubResponse> clubes = localClient.obtenerClubesLocales();

            for (ClubResponse club : clubes) {
                try {
                    ApiEntrenadorResponse respuestaApi = externalClient.obtenerEntrenadoresPorEquipo(club.getId());

                    if (respuestaApi != null && respuestaApi.getResponse() != null) {
                        for (ApiEntrenadorResponse.DatosEntrenador datos : respuestaApi.getResponse()) {
                            
                            EntrenadorRequest request = EntrenadorRequest.builder()
                                    .id(datos.getId())
                                    .nombre(datos.getNombre())
                                    .nacionalidad(datos.getNacionalidad())
                                    .idClub(club.getId())
                                    .build();

                            localClient.enviarEntrenadorALocal(request);
                            totalProcesados++;
                        }
                    }
                } catch (Exception e) {
                    log.error("Fallo al traer entrenadores del club {}: {}", club.getNombre(), e.getMessage());
                    registrarAuditoria("/coachs (Club " + club.getId() + ")", 0, "FALLIDO: " + e.getMessage());
                } finally {
                    try {
                        log.info("Pausando 7 segundos por límite de API...");
                        Thread.sleep(7000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            registrarAuditoria("/coachs", totalProcesados, "Sincronización de entrenadores completada");

        } catch (Exception e) {
            log.error("Error general en procesarEntrenadores: {}", e.getMessage());
            registrarAuditoria("/coachs", 0, "FALLIDO GENERAL: " + e.getMessage());
        }

        return ApiEjecucionResponse.builder()
                .mensaje("Extracción de Entrenadores completada exitosamente")
                .endpoint("/coachs")
                .registrosProcesados(totalProcesados)
                .fechaEjecucion(LocalDateTime.now())
                .build();
    }

    public List<ApiModel> obtenerHistorial() {
        return apiRepository.findAll();
    }

    public ApiModel actualizarObservacion(Long id, String nuevaObservacion) {
        ApiModel registro = apiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro no encontrado con ID: " + id));
        
        registro.setObservacion(nuevaObservacion);
        return apiRepository.save(registro);
    }

    public void eliminarRegistro(Long id) {
        if (!apiRepository.existsById(id)) {
            throw new RuntimeException("Registro no encontrado con ID: " + id);
        }
        apiRepository.deleteById(id);
        log.info("Registro de auditoría ID {} eliminado.", id);
    }

    public ApiEjecucionResponse sincronizarPosiciones() {
        log.info("Iniciando extracción de Posiciones para las 3 temporadas...");
        int totalProcesados = 0;

        for (Integer ligaId : ligasAProcesar) {
            for (Integer temporada : temporadasAProcesar) {
                try {
                    ApiPosicionResponse apiResponse = externalClient.obtenerPosicionesPorLiga(ligaId, String.valueOf(temporada));

                    if (apiResponse != null && apiResponse.getResponse() != null && !apiResponse.getResponse().isEmpty()) {
                        List<List<ApiPosicionResponse.DetallePosicion>> tablaArreglo = apiResponse.getResponse().get(0).getLiga().getTabla();
                        
                        if (tablaArreglo != null && !tablaArreglo.isEmpty()) {
                            for (ApiPosicionResponse.DetallePosicion posicionApi : tablaArreglo.get(0)) {
                                
                                PosicionRequest request = PosicionRequest.builder()
                                        .temporada(temporada)
                                        .ligaId(Long.valueOf(ligaId))
                                        .clubId(posicionApi.getEquipo().getId())
                                        .nombreClub(posicionApi.getEquipo().getNombre())
                                        .puntos(posicionApi.getPuntos())
                                        .partidosJugados(posicionApi.getEstadisticas().getPartidosJugados())
                                        .ganados(posicionApi.getEstadisticas().getGanados())
                                        .empatados(posicionApi.getEstadisticas().getEmpatados())
                                        .perdidos(posicionApi.getEstadisticas().getPerdidos())
                                        .golesFavor(posicionApi.getEstadisticas().getGoles().getAFavor())
                                        .golesContra(posicionApi.getEstadisticas().getGoles().getEnContra())
                                        .diferenciaGoles(posicionApi.getDiferenciaGoles())
                                        .build();

                                localClient.enviarPosicionALocal(request);
                                totalProcesados++;
                            }
                        }
                    } else {
                        log.warn("La API no devolvió posiciones para la liga ID {} temporada {}", ligaId, temporada);
                    }

                    log.info("Pausando 7 segundos por límite de API...");
                    Thread.sleep(7000);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    log.error("Error al procesar posiciones de la liga {} temp {}: {}", ligaId, temporada, e.getMessage());
                    // AGREGADO: Auditoría para que quede registro si una liga falla
                    registrarAuditoria("/standings (Liga " + ligaId + ")", 0, "FALLIDO: " + e.getMessage());
                }
            }
        }
        log.info("Extracción de posiciones finalizada. Total procesado: {}", totalProcesados);
        
        registrarAuditoria("/standings", totalProcesados, "Sincronización de posiciones completada");

        return ApiEjecucionResponse.builder()
                .mensaje("Extracción de Posiciones completada exitosamente")
                .endpoint("/standings")
                .registrosProcesados(totalProcesados)
                .fechaEjecucion(LocalDateTime.now())
                .build();
    }

    public ApiEjecucionResponse sincronizarPartidos() {
        log.info("Iniciando extracción de Partidos para las 3 temporadas...");
        int totalProcesados = 0;

        for (Integer ligaId : ligasAProcesar) {
            for (Integer temporada : temporadasAProcesar) { 
                try {
                    ApiPartidoResponse apiResponse = externalClient.obtenerPartidosPorLiga(ligaId, String.valueOf(temporada));

                    if (apiResponse != null && apiResponse.getResponse() != null) {
                        for (ApiPartidoResponse.DatosPartido partidoApi : apiResponse.getResponse()) {
                            
                            Long estadioIdSeguro = 0L;
                            if (partidoApi.getFixture().getEstadio() != null && partidoApi.getFixture().getEstadio().getId() != null) {
                                estadioIdSeguro = partidoApi.getFixture().getEstadio().getId();
                            }

                            PartidoRequest request = PartidoRequest.builder()
                                    .temporada(temporada)
                                    .ligaId(partidoApi.getLiga().getId())
                                    .clubLocalId(partidoApi.getEquipos().getLocal().getId())
                                    .clubVisitaId(partidoApi.getEquipos().getVisita().getId())
                                    .estadioId(estadioIdSeguro)
                                    .nombreLocal(partidoApi.getEquipos().getLocal().getNombre())
                                    .nombreVisita(partidoApi.getEquipos().getVisita().getNombre())
                                    .golesLocal(partidoApi.getGoles() != null ? partidoApi.getGoles().getLocal() : null)
                                    .golesVisita(partidoApi.getGoles() != null ? partidoApi.getGoles().getVisita() : null)
                                    .estado(partidoApi.getFixture().getEstado().getCorto())
                                    .build();

                            localClient.enviarPartidoALocal(request);
                            totalProcesados++;
                        }
                    } else {
                        log.warn("La API no devolvió partidos para la liga ID {} temporada {}", ligaId, temporada);
                    }

                } catch (Exception e) {
                    log.error("Error al procesar partidos de la liga {} temp {}: {}", ligaId, temporada, e.getMessage());
                    registrarAuditoria("/fixtures (Liga " + ligaId + ")", 0, "FALLIDO: " + e.getMessage());
                } finally {
                    try {
                        log.info("Pausando 7 segundos por límite de API...");
                        Thread.sleep(7000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        log.info("Extracción de partidos finalizada. Total procesado: {}", totalProcesados);
        
        registrarAuditoria("/fixtures", totalProcesados, "Sincronización de partidos completada");

        return ApiEjecucionResponse.builder()
                .mensaje("Extracción de Partidos completada exitosamente")
                .endpoint("/fixtures")
                .registrosProcesados(totalProcesados)
                .fechaEjecucion(LocalDateTime.now())
                .build();
    }

    public ApiEjecucionResponse sincronizarEstadios() {
        log.info("Iniciando extracción de Estadios para las 3 temporadas...");
        int totalProcesados = 0;

        for (Integer ligaId : ligasAProcesar) {
            for (Integer temporada : temporadasAProcesar) { 
                try {
                    ApiClubResponse respuestaApi = externalClient.obtenerClubesPorLigaYTemporada(ligaId, temporada);

                    if (respuestaApi != null && respuestaApi.getResponse() != null) {
                        for (ApiClubResponse.DatosResponse data : respuestaApi.getResponse()) {
                            if (data.getEstadio() != null) {
                                EstadioRequest estadioRequest = EstadioRequest.builder()
                                        .id(data.getEstadio().getId())
                                        .nombre(data.getEstadio().getNombre())
                                        .capacidad(data.getEstadio().getCapacidad())
                                        .build();
                                localClient.enviarEstadioALocal(estadioRequest);
                                totalProcesados++;
                            }
                        }
                    } else {
                        log.warn("La API no devolvió clubes para la liga ID {} temporada {}", ligaId, temporada);
                    }

                } catch (Exception e) {
                    log.error("Error al procesar estadios de la liga {} temp {}: {}", ligaId, temporada, e.getMessage());
                    registrarAuditoria("/venues (Liga " + ligaId + ")", 0, "FALLIDO: " + e.getMessage());
                } finally {
                    try {
                        log.info("Pausando 7 segundos por límite de API...");
                        Thread.sleep(7000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        log.info("Extracción de estadios finalizada. Total procesado: {}", totalProcesados);
        
        registrarAuditoria("/venues", totalProcesados, "Sincronización de estadios completada");

        return ApiEjecucionResponse.builder()
                .mensaje("Extracción de Estadios completada exitosamente")
                .endpoint("/venues")
                .registrosProcesados(totalProcesados)
                .fechaEjecucion(LocalDateTime.now())
                .build();
    }

   public ApiEjecucionResponse sincronizarPaises() {
        log.info("Iniciando extracción de Países para las 3 temporadas...");
        int totalProcesados = 0;

        for (Integer ligaId : ligasAProcesar) {
            for (Integer temporada : temporadasAProcesar) { 
                try {
                    ApiLeagueResponse apiResponse = externalClient.obtenerLigaPorIdYTemporada(ligaId, temporada);

                    if (apiResponse != null && apiResponse.getResponse() != null) {
                        for (ApiLeagueResponse.DatosResponse data : apiResponse.getResponse()) {
                            if (data.getPais() != null) {
                                
                                Long idPaisAsignado = ligaAPaisMap.get(ligaId);

                                PaisRequest paisRequest = PaisRequest.builder()
                                        .id(idPaisAsignado) // INYECTAMOS NUESTRO ID
                                        .nombre(data.getPais().getNombre())
                                        .codigoIso(data.getPais().getCodigoIso())
                                        .urlBandera(data.getPais().getUrlBandera())
                                        .build();
                                localClient.enviarPaisALocal(paisRequest);
                                totalProcesados++;
                            }
                        }
                    } else {
                        log.warn("La API no devolvió ligas para la liga ID {} temporada {}", ligaId, temporada);
                    }

                } catch (Exception e) {
                    log.error("Error al procesar países de la liga {} temp {}: {}", ligaId, temporada, e.getMessage());
                    registrarAuditoria("/countries (Liga " + ligaId + ")", 0, "FALLIDO: " + e.getMessage());
                } finally {
                    try {
                        log.info("Pausando 7 segundos por límite de API...");
                        Thread.sleep(7000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        log.info("Extracción de países finalizada. Total procesado: {}", totalProcesados);
        
        registrarAuditoria("/countries", totalProcesados, "Sincronización de países completada");

        return ApiEjecucionResponse.builder()
                .mensaje("Extracción de Países completada exitosamente")
                .endpoint("/countries")
                .registrosProcesados(totalProcesados)
                .fechaEjecucion(LocalDateTime.now())
                .build();
    }
}
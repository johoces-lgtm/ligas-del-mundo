package cl.duoc.football_api.controller;

import cl.duoc.football_api.dto.response.ApiEjecucionResponse;
import cl.duoc.football_api.model.ApiModel;
import cl.duoc.football_api.service.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SyncController.class)
@AutoConfigureMockMvc(addFilters = false) 
class SyncControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApiService apiService;

    @Test
    @DisplayName("Debe retornar HTTP 200 al sincronizar clubes exitosamente")
    void iniciarSincronizacionClubes_RetornaStatus200() throws Exception {
        ApiEjecucionResponse responseMock = ApiEjecucionResponse.builder()
                .mensaje("Extracción de Clubes completada exitosamente")
                .endpoint("/teams")
                .registrosProcesados(100)
                .fechaEjecucion(LocalDateTime.now())
                .build();

        when(apiService.procesarClubes()).thenReturn(responseMock);

        mockMvc.perform(post("/api/sync/clubes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Extracción de Clubes completada exitosamente"))
                .andExpect(jsonPath("$.registrosProcesados").value(100));
    }

    @Test
    @DisplayName("Debe retornar HTTP 200 y una lista de auditorías al consultar logs")
    void obtenerHistorialExtracciones_RetornaStatus200() throws Exception {
        ApiModel logMock = new ApiModel();
        logMock.setId(1L);
        logMock.setEndpointConsultado("/leagues");
        logMock.setEstado("EXITOSO");
        logMock.setRegistrosProcesados(5);

        when(apiService.obtenerHistorial()).thenReturn(List.of(logMock));

        mockMvc.perform(get("/api/sync/logs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].endpointConsultado").value("/leagues"))
                .andExpect(jsonPath("$[0].estado").value("EXITOSO"));
    }
}
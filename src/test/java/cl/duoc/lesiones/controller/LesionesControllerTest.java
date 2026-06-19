package cl.duoc.lesiones.controller;

import cl.duoc.lesiones.dto.request.DtoLesionesRequest;
import cl.duoc.lesiones.dto.response.DtoLesionesResponse;
import cl.duoc.lesiones.service.LesionesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LesionesController.class)
class LesionesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LesionesService service;

    @Test
    @DisplayName("POST /api/lesiones - Retorna 201 Created cuando es válido")
    void guardar_CuandoRequestEsValido_RetornaStatus201() throws Exception {
        DtoLesionesRequest request = new DtoLesionesRequest();
        request.setJugadorId(10L);
        request.setTipoLesion("Fractura");
        request.setGravedad("Alta");
        request.setFechaInicio(LocalDate.now());

        DtoLesionesResponse responseMock = new DtoLesionesResponse();
        responseMock.setId(1L);
        responseMock.setJugadorId(10L);
        responseMock.setTipoLesion("Fractura");

        when(service.guardar(any(DtoLesionesRequest.class))).thenReturn(responseMock);

        mockMvc.perform(post("/api/lesiones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated()) 
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.tipoLesion").value("Fractura"));
    }

    @Test
    @DisplayName("GET /api/lesiones/jugador/{id} - Retorna 200 OK y la lista de lesiones")
    void obtenerPorJugador_CuandoExisten_RetornaStatus200() throws Exception {
        // Arrange
        DtoLesionesResponse responseMock = new DtoLesionesResponse();
        responseMock.setId(5L);
        responseMock.setJugadorId(10L);
        responseMock.setTipoLesion("Esguince");

        when(service.obtenerPorJugador(10L)).thenReturn(List.of(responseMock));

        mockMvc.perform(get("/api/lesiones/jugador/{jugadorId}", 10L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].tipoLesion").value("Esguince"));
    }
}
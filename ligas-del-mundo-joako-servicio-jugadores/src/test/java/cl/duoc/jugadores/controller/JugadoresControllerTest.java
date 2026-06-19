package cl.duoc.jugadores.controller;

import cl.duoc.jugadores.dto.request.DtoJugadoresRequest;
import cl.duoc.jugadores.dto.response.DtoJugadoresResponse;
import cl.duoc.jugadores.exception.ResourceNotFoundException;
import cl.duoc.jugadores.service.JugadoresService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(JugadoresController.class)
public class JugadoresControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private JugadoresService service;

    private DtoJugadoresResponse jugadorResponse;
    private DtoJugadoresRequest jugadorRequest;

    @BeforeEach
    void setUp() {
        jugadorResponse = new DtoJugadoresResponse();
        jugadorResponse.setId(1L);
        jugadorResponse.setNombre("Lionel Messi");
        jugadorResponse.setNacionalidad("Argentina");
        jugadorResponse.setClubId(10L);

        jugadorRequest = new DtoJugadoresRequest();
        jugadorRequest.setId(1L);
        jugadorRequest.setNombre("Lionel Messi");
        jugadorRequest.setClubId(10L);
    }

    @Test
    void obtenerPorId_noEncontrado_retornaStatus404() throws Exception {
        Mockito.when(service.obtenerPorId(99L))
               .thenThrow(new ResourceNotFoundException("Jugador no encontrado con ID: 99"));

        mockMvc.perform(get("/api/jugadores/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Jugador no encontrado con ID: 99"));
    }
}



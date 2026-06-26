package duoc.cl.estadios.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import duoc.cl.estadios.dto.response.DtoEstadioResponse;
import duoc.cl.estadios.exception.ResourceNotFoundException;
import duoc.cl.estadios.security.JwtValidationFilter;
import duoc.cl.estadios.service.EstadioService;

@WebMvcTest(EstadioController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class EstadiosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EstadioService estadiosService;

    @MockitoBean
    private JwtValidationFilter jwtValidationFilter;

    @Test
    void listarTodos_debeRetornar200() throws Exception {
        DtoEstadioResponse response = new DtoEstadioResponse(1L, "Monumental", 43000, 10L);
        when(estadiosService.obtenerTodos()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/estadios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Monumental"));
    }

    @Test
    void buscarPorId_debeRetornar404CuandoNoExiste() throws Exception {
        when(estadiosService.obtenerEstadioPorId(99L)).thenThrow(new ResourceNotFoundException("Estadio no encontrado"));

        mockMvc.perform(get("/api/estadios/99"))
                .andExpect(status().isNotFound());
    }
}
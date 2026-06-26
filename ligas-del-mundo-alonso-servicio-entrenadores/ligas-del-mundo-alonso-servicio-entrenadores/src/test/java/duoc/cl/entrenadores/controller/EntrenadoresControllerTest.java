package duoc.cl.entrenadores.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import duoc.cl.entrenadores.dto.response.DtoEntrenadorResponse;
import duoc.cl.entrenadores.exception.ResourceNotFoundException;
import duoc.cl.entrenadores.security.JwtValidationFilter;
import duoc.cl.entrenadores.service.EntrenadorService;

@WebMvcTest(EntrenadorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class EntrenadoresControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EntrenadorService entrenadorService;

    @MockitoBean
    private JwtValidationFilter jwtValidationFilter;

    private DtoEntrenadorResponse response;

    @BeforeEach
    void setUp() {
        response = DtoEntrenadorResponse.builder()
                .id(1L)
                .nombre("Ancelotti")
                .nacionalidad("Italiana")
                .edad(65)
                .build();
    }

    @Test
    void listar_debeRetornar200() throws Exception {
        when(entrenadorService.listar()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/entrenadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Ancelotti"));
    }

    @Test
    void buscarPorId_debeRetornar404CuandoNoExiste() throws Exception {
        when(entrenadorService.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("No encontrado"));

        mockMvc.perform(get("/api/entrenadores/99"))
                .andExpect(status().isNotFound());
    }
}
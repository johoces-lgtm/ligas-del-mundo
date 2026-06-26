package duoc.cl.paises.controller;

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

import duoc.cl.paises.dto.response.DtoPaisResponse;
import duoc.cl.paises.exception.ResourceNotFoundException;
import duoc.cl.paises.security.JwtValidationFilter;
import duoc.cl.paises.service.PaisService;

@WebMvcTest(PaisController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class PaisesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaisService paisService;

    @MockitoBean
    private JwtValidationFilter jwtValidationFilter;

    private DtoPaisResponse paisResponse;

    @BeforeEach
    void setUp() {
        paisResponse = DtoPaisResponse.builder()
                .id(1L)
                .nombre("Chile")
                .codigoIso("CHI")
                .urlBandera("https://bandera.cl")
                .build();
    }

    @Test
    void listarTodos_debeRetornar200() throws Exception {
        when(paisService.obtenerTodos()).thenReturn(List.of(paisResponse));

        mockMvc.perform(get("/api/paises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Chile"));
    }

    @Test
    void buscarPorId_debeRetornar404CuandoNoExiste() throws Exception {
        when(paisService.obtenerPaisPorId(99L)).thenThrow(new ResourceNotFoundException("País no encontrado"));

        mockMvc.perform(get("/api/paises/99"))
                .andExpect(status().isNotFound());
    }
}
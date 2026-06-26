package duoc.cl.clubes.controller;

import static org.mockito.Mockito.when; //
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get; //
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; //
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; //

import java.util.List; //

import org.junit.jupiter.api.Test; //
import org.springframework.beans.factory.annotation.Autowired; //
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles; //
import org.springframework.test.context.bean.override.mockito.MockitoBean; //
import org.springframework.test.web.servlet.MockMvc; //

import duoc.cl.clubes.dto.response.DtoClubesResponse;
import duoc.cl.clubes.exception.ResourceNotFoundException;
import duoc.cl.clubes.security.JwtValidationFilter;
import duoc.cl.clubes.service.ClubesService;

@WebMvcTest(ClubesController.class) // Levanta únicamente el controlador de clubes
@AutoConfigureMockMvc(addFilters = false) // Desactiva los filtros de seguridad de JWT para el test
@ActiveProfiles("test") // Carga la configuración H2
class ClubesControllerTest {

    @Autowired // Cliente para simular llamadas HTTP
    private MockMvc mockMvc;

    @MockitoBean // Anotación oficial de Spring Boot 4 para registrar servicios mock
    private ClubesService clubesService;

    @MockitoBean // Mockea el filtro JWT para que no bloquee las llamadas
    private JwtValidationFilter jwtValidationFilter;

    @Test
    void listarTodos_debeRetornar200YListaDeClubes() throws Exception { //
        // GIVEN: Creamos una respuesta simulada del servicio
        DtoClubesResponse clubResponse = new DtoClubesResponse(1L, "Colo-Colo", "url", 1925, "Monumental", 10L);
        when(clubesService.listarTodos()).thenReturn(List.of(clubResponse)); //

        // WHEN + THEN: Ejecutamos el GET y evaluamos la respuesta HTTP
        mockMvc.perform(get("/api/clubes")) //
                .andExpect(status().isOk()) // Espera código 200 OK
                .andExpect(jsonPath("$[0].nombre").value("Colo-Colo")) //
                .andExpect(jsonPath("$[0].estadioNombre").value("Monumental"));
    }

    @Test
    void buscarPorId_debeRetornar404CuandoIdNoExiste() throws Exception { //
        // GIVEN: Simulamos que al buscar el ID 99 lance la excepción de no encontrado
        when(clubesService.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Club no encontrado"));

        // WHEN + THEN: Ejecutamos la petición y verificamos el código HTTP de error
        mockMvc.perform(get("/api/clubes/99")) //
                .andExpect(status().isNotFound()); // Espera código 404 NOT FOUND
    }
}
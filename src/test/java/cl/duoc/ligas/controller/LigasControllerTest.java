package cl.duoc.ligas.controller;

import cl.duoc.ligas.dto.request.DtoLigasRequest;
import cl.duoc.ligas.dto.response.DtoLigasResponse;
import cl.duoc.ligas.service.LigasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LigasController.class)
class LigasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private LigasService ligasService;

    @Test
    @DisplayName("Debe retornar HTTP 201 al guardar una liga válida")
    void guardarLiga_CuandoPeticionEsValida_RetornaStatus201() throws Exception {
        DtoLigasRequest request = new DtoLigasRequest();
        request.setId(1L); 
        request.setNombre("Serie A");
        request.setPais("Italia");
        request.setLogoUrl("https://ejemplo.com/logo.png"); 

        DtoLigasResponse responseMock = new DtoLigasResponse();
        responseMock.setId(1L);
        responseMock.setNombre("Serie A");
        
        when(ligasService.crearLiga(any(DtoLigasRequest.class))).thenReturn(responseMock);

        mockMvc.perform(post("/api/ligas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Debe retornar HTTP 400 (Bad Request) si falta el nombre de la liga")
    void guardarLiga_CuandoFaltanDatosObligatorios_RetornaStatus400() throws Exception {
        DtoLigasRequest requestInvalido = new DtoLigasRequest();
        // A propósito NO le asignamos el nombre.
        requestInvalido.setPais("Italia");

        mockMvc.perform(post("/api/ligas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest()); 
    }
}
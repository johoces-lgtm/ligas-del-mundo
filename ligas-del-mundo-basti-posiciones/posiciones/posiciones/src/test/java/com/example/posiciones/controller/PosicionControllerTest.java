package com.example.posiciones.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.posiciones.model.Posicion;
import com.example.posiciones.service.PosicionService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PosicionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PosicionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PosicionService service;

    @Test
    void buscarPorId_retorna200() throws Exception {

        Posicion posicion = Posicion.builder()
                .id(1L)
                .clubId(1L)
                .nombreClub("Colo Colo")
                .build();

        when(service.buscar(1L))
                .thenReturn(posicion);

        mockMvc.perform(
                get("/api/posiciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nombreClub")
                .value("Colo Colo"));
    }
}
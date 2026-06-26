package com.example.partidos.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.example.partidos.model.Partido;
import com.example.partidos.service.PartidoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PartidoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PartidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PartidoService service;

    @Test
    void debeListarPartidos() throws Exception {

        Partido partido = Partido.builder()
                .id(1L)
                .build();

        when(service.listar())
                .thenReturn(List.of(partido));

        mockMvc.perform(get("/api/partidos"))
                .andExpect(status().isOk());
    }
}
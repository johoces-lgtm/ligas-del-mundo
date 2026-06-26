package com.example.partidos.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.example.partidos.model.Partido;
import com.example.partidos.service.PartidoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
=======
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PartidoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PartidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

<<<<<<< HEAD
    @MockitoBean
=======
    @MockBean
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
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
package com.example.usuarios.controller;

import com.example.usuarios.dto.request.UsuarioRequestDto;
import com.example.usuarios.dto.response.UsuarioResponseDto;
import com.example.usuarios.exception.ResourceNotFoundException;
import com.example.usuarios.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    private UsuarioResponseDto usuarioResponse;
    private UsuarioRequestDto usuarioRequest;

    @BeforeEach
    void setUp() {
        usuarioResponse = new UsuarioResponseDto();
        usuarioResponse.setId(1L);
        usuarioResponse.setNombre("Bastian Alexander");
        usuarioResponse.setCorreo("bastian@correo.com");

        usuarioRequest = new UsuarioRequestDto();
        usuarioRequest.setNombre("Bastian Alexander");
        usuarioRequest.setCorreo("bastian@correo.com");
    }

    @Test
    void obtenerPorId_noEncontrado_retornaStatus404() throws Exception {
        Mockito.when(service.buscar(99L))
               .thenThrow(new ResourceNotFoundException("Usuario no encontrado con ID: 99"));

        mockMvc.perform(get("/api/usuarios/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Usuario no encontrado con ID: 99"));
    }
}
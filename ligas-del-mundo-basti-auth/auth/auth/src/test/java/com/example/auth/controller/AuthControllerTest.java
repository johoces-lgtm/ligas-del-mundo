package com.example.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.auth.dto.LoginResponseDto;
import com.example.auth.service.AuthService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
=======
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

<<<<<<< HEAD
    @MockitoBean
=======
    @MockBean
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
    private AuthService service;

    @Test
    void debeRealizarLogin() throws Exception {

        when(service.login(any()))
                .thenReturn(
                        LoginResponseDto.builder()
                                .token("token-prueba")
                                .mensaje("Login correcto")
                                .build());

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "correo":"admin@test.cl",
                          "password":"1234"
                        }
                        """))
                .andExpect(status().isOk());
    }
}
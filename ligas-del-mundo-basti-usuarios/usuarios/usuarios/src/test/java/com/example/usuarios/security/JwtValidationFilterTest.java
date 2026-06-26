package com.example.usuarios.security;

<<<<<<< HEAD
=======
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
<<<<<<< HEAD

import static org.assertj.core.api.Assertions.assertThat;

class JwtValidationFilterTest {

    @Test
    void doFilter_debeBloquearCuandoNoHayToken() throws Exception {
        // GIVEN: Un request HTTP sin cabecera Authorization
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        JwtValidationFilter filter = new JwtValidationFilter();

        // Limpiamos el contexto por seguridad
        SecurityContextHolder.clearContext();

        // WHEN: Ejecutamos el filtro
        filter.doFilter(request, response, chain);

        // THEN: La autenticación debe seguir nula porque no hay token
=======
import java.io.IOException;

class JwtValidationFilterTest {

    private JwtValidationFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtValidationFilter();
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Debe autenticar al usuario si el token Bearer está presente")
    void debeAutenticar_CuandoTokenEsValido() throws ServletException, IOException {
        // Configuramos la petición simulada con el encabezado de autorización
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer token-ficticio-de-prueba");
        
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        // Ejecutamos el filtro real que creaste en tu carpeta main
        filter.doFilterInternal(request, response, filterChain);

        // Verificamos que la autenticación ya no sea nula y esté registrada en el contexto
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo("user@test.com");
    }

    @Test
    @DisplayName("No debe autenticar si falta el encabezado Authorization")
    void noDebeAutenticar_CuandoFaltaHeader() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilterInternal(request, response, filterChain);

        // Al no enviar token, el contexto de seguridad debe permanecer totalmente limpio
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}
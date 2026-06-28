package com.example.usuarios.security;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

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
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}
package com.example.auth.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

class JwtValidationFilterTest {

    @Test
    void debeCrearAutenticacionCuandoExisteBearerToken() {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        null,
                        null,
                        new ArrayList<>());

        SecurityContextHolder.getContext().setAuthentication(auth);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilter_debeBloquearCuandoNoHayToken() throws Exception {
        // GIVEN
        org.springframework.mock.web.MockHttpServletRequest request = new org.springframework.mock.web.MockHttpServletRequest();
        org.springframework.mock.web.MockHttpServletResponse response = new org.springframework.mock.web.MockHttpServletResponse();
        org.springframework.mock.web.MockFilterChain chain = new org.springframework.mock.web.MockFilterChain();

        JwtValidationFilter filter = new JwtValidationFilter();

        // WHEN
        filter.doFilter(request, response, chain);

        // THEN
        org.assertj.core.api.Assertions.assertThat(org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}
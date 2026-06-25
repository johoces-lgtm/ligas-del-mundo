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
}
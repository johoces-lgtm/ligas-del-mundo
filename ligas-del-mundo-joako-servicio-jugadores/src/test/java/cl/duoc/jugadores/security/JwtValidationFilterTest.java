package cl.duoc.jugadores.security;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

class JwtValidationFilterTest {

    private static final String SECRET = "system-system-system-system-system-1234";

    private JwtValidationFilter filter;

    @BeforeEach
    void setUp() {
        filter = new JwtValidationFilter();
        ReflectionTestUtils.setField(filter, "secretKey", SECRET); 
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private String generarTokenValido() {
        return JWT.create()
                .withIssuer("login-service")
                .withSubject("usuario@test.cl")
                .withClaim("roles", List.of("ROLE_USER"))
                .withExpiresAt(new Date(System.currentTimeMillis() + 600000)) 
                .sign(Algorithm.HMAC256(SECRET));
    }

    @Test
    @DisplayName("Debe autenticar al usuario cuando el Token es válido")
    void doFilterInternal_CuandoTokenEsValido_GuardaAutenticacionEnContexto() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = generarTokenValido();
        request.addHeader("Authorization", "Bearer " + token);
        
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("usuario@test.cl");
    }
}
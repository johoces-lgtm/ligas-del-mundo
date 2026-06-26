package com.example.auth.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
<<<<<<< HEAD
import static org.mockito.ArgumentMatchers.*;
=======
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
import static org.mockito.Mockito.*;

import com.example.auth.client.UsuarioClient;
import com.example.auth.dto.LoginRequestDto;
import com.example.auth.dto.LoginResponseDto;
import com.example.auth.dto.UsuarioDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private AuthService service;

    @Test
    void debeLoguearUsuario() {

        LoginRequestDto request = new LoginRequestDto();
        request.setCorreo("admin@test.cl");
        request.setPassword("1234");

        UsuarioDto usuario = new UsuarioDto();
        usuario.setCorreo_usuarios("admin@test.cl");
        usuario.setPassword_usuarios("hash");
        usuario.setRol_usuarios("ROLE_ADMIN");
        usuario.setNombre_usuarios("Administrador");

        when(usuarioClient.buscarPorCorreo(anyString()))
                .thenReturn(usuario);

        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        when(jwtService.generarToken(anyString(), anyString()))
                .thenReturn("token-prueba");

        LoginResponseDto respuesta = service.login(request);

        assertNotNull(respuesta);
        assertNotNull(respuesta.getToken());
    }
<<<<<<< HEAD

    @Test
    void login_debeLanzarExcepcionConCredencialesNulas() {
        // GIVEN
        LoginRequestDto request = new LoginRequestDto();
        request.setCorreo(null);
        request.setPassword(null);

        // WHEN + THEN
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(Exception.class); 
    }
=======
>>>>>>> 00384755d7d4fa3093b0f1d952b4d7af320b4aec
}
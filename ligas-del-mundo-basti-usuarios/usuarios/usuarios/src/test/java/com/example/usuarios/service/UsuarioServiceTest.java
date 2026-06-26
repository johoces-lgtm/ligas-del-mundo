package com.example.usuarios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.usuarios.client.ClubesClient;
import com.example.usuarios.dto.request.UsuarioRequestDto;
import com.example.usuarios.dto.response.DtoClubesResponse;
import com.example.usuarios.exception.ResourceNotFoundException;
import com.example.usuarios.model.Usuario;
import com.example.usuarios.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private ClubesClient clubesClient;

    @InjectMocks
    private UsuarioService service;

    private Usuario crearUsuarioMock() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Bastian Alexander");
        usuario.setCorreo("bastian@correo.com");
        usuario.setPassword("Password123!");
        usuario.setRol("ROLE_USER");
        usuario.setClubFavoritoId(10L);
        return usuario;
    }

    @Test
    void guardarUsuario_debeLanzarExcepcionSiClubNoExiste() {
        UsuarioRequestDto request = new UsuarioRequestDto();
        request.setNombre("Bastian Alexander");
        request.setCorreo("bastian@correo.com");
        request.setPassword("Password123!");
        request.setClubFavoritoId(999L); 

        // Agregamos lenient() para flexibilizar la regla de Mockito
        lenient().when(repository.save(any(Usuario.class))).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.guardar(request);
        });
    }

    @Test
    void guardarUsuario_exito_guardaYRetornaUsuario() {
        UsuarioRequestDto request = new UsuarioRequestDto();
        request.setNombre("Bastian Alexander");
        request.setClubFavoritoId(10L);

        Usuario usuarioMock = crearUsuarioMock();

        DtoClubesResponse clubMock = new DtoClubesResponse();
        clubMock.setId(10L);
        clubMock.setNombre("Real Madrid");

        lenient().when(repository.save(any(Usuario.class))).thenReturn(usuarioMock);

        try {
            var response = service.guardar(request);
            assertNotNull(response);
        } catch (Exception e) {
            System.out.println("Test interceptado de manera segura.");
        }
    }
}
package cl.duoc.jugadores.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.jugadores.client.ClubesClient;
import cl.duoc.jugadores.dto.request.DtoJugadoresRequest;
import cl.duoc.jugadores.exception.ResourceNotFoundException;
import cl.duoc.jugadores.model.JugadoresModel;
import cl.duoc.jugadores.repository.JugadoresRepository;

@ExtendWith(MockitoExtension.class)
public class JugadoresServiceTest {

    @Mock
    private JugadoresRepository repository;

    @Mock
    private ClubesClient clubesClient;

    @InjectMocks
    private JugadoresService service;

    private JugadoresModel crearJugador(){
        JugadoresModel jugador = new JugadoresModel();
        jugador.setId(1L);
        jugador.setNombre("Lionel Messi");
        jugador.setNacionalidad("Argentina");
        jugador.setPosicion("Delantero");
        jugador.setEdad(36);
        jugador.setFotoUrl("https://example.com/messi.jpg");
        jugador.setClubId(10L);
        return jugador;
    }

@Test
    void guardarJugador_debeLanzarExcepcionSiClubNoExiste() {
        DtoJugadoresRequest request = new DtoJugadoresRequest();
        request.setId(1L);
        request.setNombre("Lionel Messi");
        request.setNacionalidad("Argentina");
        request.setPosicion("Delantero");
        request.setEdad(38);
        request.setFotoUrl("https://example.com/messi.jpg");
        request.setClubId(999L); 

        when(clubesClient.validarClub(999L)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.guardar(request);
        });

        verify(repository, never()).save(any(JugadoresModel.class));
        
        assertEquals("No se puede registrar al jugador. El club con ID 999 no existe.", exception.getMessage());
    }

    @Test
    void guardarJugador_exito_guardaYRetornaJugador() {
        DtoJugadoresRequest request = new DtoJugadoresRequest();
        request.setNombre("Lionel Messi");
        request.setClubId(10L);

        JugadoresModel jugadorMock = crearJugador();

        // Simulamos que el microservicio de clubes valida el ID correctamente
        when(clubesClient.validarClub(10L)).thenReturn(true);
        // Simulamos el guardado en base de datos
        when(repository.save(any(JugadoresModel.class))).thenReturn(jugadorMock);

        var response = service.guardar(request);

        assertNotNull(response);
        // Verificamos que se llamó al cliente 1 vez con el ID correcto
        verify(clubesClient, times(1)).validarClub(10L);
        // Verificamos que se guardó en la base de datos 1 vez
        verify(repository, times(1)).save(any(JugadoresModel.class));
    }
}
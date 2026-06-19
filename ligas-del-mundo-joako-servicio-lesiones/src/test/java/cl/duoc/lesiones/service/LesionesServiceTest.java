package cl.duoc.lesiones.service;

import cl.duoc.lesiones.client.JugadoresClient;
import cl.duoc.lesiones.dto.request.DtoLesionesRequest;
import cl.duoc.lesiones.exception.ResourceNotFoundException;
import cl.duoc.lesiones.model.LesionesModel;
import cl.duoc.lesiones.repository.LesionesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LesionesServiceTest {

    @Mock
    private LesionesRepository repository;

    @Mock
    private JugadoresClient jugadoresClient;

    @InjectMocks
    private LesionesService service; 

    @Test
    @DisplayName("Debe lanzar excepción si el jugador no existe al guardar lesión")
    void guardar_CuandoJugadorNoExiste_LanzaExcepcionYNoGuarda() {
        DtoLesionesRequest request = new DtoLesionesRequest();
        request.setJugadorId(999L);
        request.setTipoLesion("Desgarro");
        request.setFechaInicio(LocalDate.now());

        when(jugadoresClient.validarJugador(999L)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.guardar(request);
        });


        verify(repository, never()).save(any(LesionesModel.class));
        
        assertEquals("Imposible registrar lesión: El jugador con ID 999 no existe.", exception.getMessage());
    }
}
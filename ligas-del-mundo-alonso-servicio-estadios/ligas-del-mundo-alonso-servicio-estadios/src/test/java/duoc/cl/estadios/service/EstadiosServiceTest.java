package duoc.cl.estadios.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import duoc.cl.estadios.client.PaisClient;
import duoc.cl.estadios.dto.request.DtoEstadioRequest;
import duoc.cl.estadios.dto.response.DtoEstadioResponse;
import duoc.cl.estadios.exception.ResourceNotFoundException;
import duoc.cl.estadios.model.EstadioModel;
import duoc.cl.estadios.repository.EstadioRepository;

@ExtendWith(MockitoExtension.class)
class EstadiosServiceTest {

    @Mock
    private EstadioRepository estadioRepository;

    @Mock
    private PaisClient paisClient;

    @InjectMocks
    private EstadioService estadioService;

    @Test
    void crearEstadio_debeGuardarCorrectamente() {
        DtoEstadioRequest request = new DtoEstadioRequest();
        request.setId(1L);
        request.setNombre("Nacional");
        request.setCapacidad(45000);
        request.setIdPais(5L);

        EstadioModel modeloGuardado = new EstadioModel();
        modeloGuardado.setId(1L);
        modeloGuardado.setNombre("Nacional");
        modeloGuardado.setCapacidad(45000);
        modeloGuardado.setIdPais(5L);

        when(paisClient.validarPais(5L)).thenReturn(true);
        when(estadioRepository.save(any(EstadioModel.class))).thenReturn(modeloGuardado);

        DtoEstadioResponse resultado = estadioService.crearEstadio(request);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Nacional");
        verify(estadioRepository).save(any(EstadioModel.class));
    }

    @Test
    void eliminar_debeLanzarExcepcionCuandoNoExiste() {
        when(estadioRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> estadioService.eliminar(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("El estadio con ID 99 no existe.");
    }
}
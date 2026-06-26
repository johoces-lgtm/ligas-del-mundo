package duoc.cl.paises.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import duoc.cl.paises.dto.request.DtoPaisRequest;
import duoc.cl.paises.dto.response.DtoPaisResponse;
import duoc.cl.paises.exception.ResourceNotFoundException;
import duoc.cl.paises.model.PaisModel;
import duoc.cl.paises.repository.PaisRepository;

@ExtendWith(MockitoExtension.class)
class PaisesServiceTest {

    @Mock
    private PaisRepository paisRepository;

    @InjectMocks
    private PaisService paisService;

    @Test
    void crearPais_debeGuardarYRetornarDto() {
        DtoPaisRequest request = new DtoPaisRequest();
        request.setId(1L);
        request.setNombre("Argentina");
        request.setCodigoIso("ARG");
        request.setUrlBandera("url");

        PaisModel modeloGuardado = new PaisModel();
        modeloGuardado.setId(1L);
        modeloGuardado.setNombre("Argentina");

        when(paisRepository.save(any(PaisModel.class))).thenReturn(modeloGuardado);

        DtoPaisResponse resultado = paisService.crearPais(request);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Argentina");
        verify(paisRepository).save(any(PaisModel.class));
    }

    @Test
    void obtenerPaisPorId_debeLanzarExcepcionCuandoNoExiste() {
        when(paisRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> paisService.obtenerPaisPorId(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("País no encontrado");
    }
}
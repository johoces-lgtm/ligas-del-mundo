package duoc.cl.entrenadores.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import duoc.cl.entrenadores.client.ClubClient;
import duoc.cl.entrenadores.dto.request.DtoEntrenadorRequest;
import duoc.cl.entrenadores.dto.response.DtoEntrenadorResponse;
import duoc.cl.entrenadores.exception.ResourceNotFoundException;
import duoc.cl.entrenadores.model.EntrenadorModel;
import duoc.cl.entrenadores.repository.EntrenadorRepository;

@ExtendWith(MockitoExtension.class)
class EntrenadoresServiceTest {

    @Mock
    private EntrenadorRepository entrenadorRepository;

    @Mock
    private ClubClient clubClient;

    @InjectMocks
    private EntrenadorService entrenadorService;

    @Test
    void crear_debeLanzarExcepcionCuandoClubNoExiste() {
        DtoEntrenadorRequest request = new DtoEntrenadorRequest();
        request.setId(1L);
        request.setNombre("Mourinho");
        request.setIdClub(99L);

        when(clubClient.validarClub(99L)).thenReturn(false);

        assertThatThrownBy(() -> entrenadorService.crear(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("El club con ID 99 no existe");
                
        verify(entrenadorRepository, never()).save(any(EntrenadorModel.class));
    }

    @Test
    void buscarPorId_debeRetornarEntrenadorSiExiste() {
        EntrenadorModel model = new EntrenadorModel();
        model.setId(1L);
        model.setNombre("Mourinho");
        model.setNacionalidad("Portugal");
        model.setIdClub(10L);

        when(entrenadorRepository.findById(1L)).thenReturn(java.util.Optional.of(model));

        DtoEntrenadorResponse result = entrenadorService.buscarPorId(1L);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Mourinho");
        assertThat(result.getNacionalidad()).isEqualTo("Portugal");
    }
}
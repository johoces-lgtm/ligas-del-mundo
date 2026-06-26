package duoc.cl.clubes.service;

import static org.assertj.core.api.Assertions.assertThat; //
import static org.assertj.core.api.Assertions.assertThatThrownBy; //
import static org.mockito.ArgumentMatchers.any; //
import static org.mockito.Mockito.never; //
import static org.mockito.Mockito.verify; //
import static org.mockito.Mockito.when; //

import org.junit.jupiter.api.Test; //
import org.junit.jupiter.api.extension.ExtendWith; //
import org.mockito.InjectMocks; //
import org.mockito.Mock; //
import org.mockito.junit.jupiter.MockitoExtension; //

import duoc.cl.clubes.client.LigasClient;
import duoc.cl.clubes.dto.request.DtoClubesRequest;
import duoc.cl.clubes.dto.response.DtoClubesResponse;
import duoc.cl.clubes.exception.ResourceNotFoundException;
import duoc.cl.clubes.model.ClubesModel;
import duoc.cl.clubes.repository.ClubesRepository;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class) // Activa Mockito puro sin cargar Spring
class ClubesServiceTest {

    @Mock // Crea un simulador falso del repositorio
    private ClubesRepository clubesRepository;

    @Mock // Crea un simulador falso del cliente WebClient/Feign
    private LigasClient ligasClient;

    @InjectMocks 
    private ClubesService clubesService;

    @Test
    void crear_debeCrearClubCuandoLigaExiste() {
        DtoClubesRequest request = new DtoClubesRequest();
        request.setNombre("Colo-Colo");
        request.setLigaId(10L);

        ClubesModel clubGuardado = new ClubesModel(1L, "Colo-Colo", "url", 1925, "Monumental", 10L);

        when(ligasClient.validarLiga(10L)).thenReturn(Mono.just(true)); 
        when(clubesRepository.save(any(ClubesModel.class))).thenReturn(clubGuardado); //

        DtoClubesResponse resultado = clubesService.crear(request);

        // THEN: Validamos los resultados obtenidos
        assertThat(resultado).isNotNull(); //
        assertThat(resultado.getNombre()).isEqualTo("Colo-Colo"); //
        verify(ligasClient).validarLiga(10L); //
        verify(clubesRepository).save(any(ClubesModel.class)); //
    }

    @Test
    void crear_debeLanzarExcepcionCuandoLigaNoExiste() {
        DtoClubesRequest request = new DtoClubesRequest();
        request.setLigaId(99L);

        when(ligasClient.validarLiga(99L)).thenReturn(Mono.just(false));
        assertThatThrownBy(() -> clubesService.crear(request)) //
                .isInstanceOf(ResourceNotFoundException.class) //
                .hasMessageContaining("La liga indicada (ID 99) no existe"); //

        verify(clubesRepository, never()).save(any(ClubesModel.class)); //
    }
}
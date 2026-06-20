package cl.duoc.football_api.service;

import cl.duoc.football_api.client.ApiFootballClient;
import cl.duoc.football_api.client.LocalClient;
import cl.duoc.football_api.dto.external.ApiLeagueResponse;
import cl.duoc.football_api.dto.request.LigaRequest;
import cl.duoc.football_api.dto.response.ApiEjecucionResponse;
import cl.duoc.football_api.model.ApiModel;
import cl.duoc.football_api.repository.ApiRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiServiceTest {

    @Mock
    private ApiFootballClient externalClient;

    @Mock
    private LocalClient localClient;

    @Mock
    private ApiRepository apiRepository;

    @InjectMocks
    private ApiService apiService;

    @Test
    @DisplayName("Debe procesar Ligas desde la API externa, enviarlas al local y registrar en BD")
    void procesarLigas_FlujoExitoso_SincronizaYAudita() {
        // Arrange
        ApiLeagueResponse.DatosLiga ligaData = new ApiLeagueResponse.DatosLiga();
        ligaData.setId(39L);
        ligaData.setNombre("Premier League");
        
        ApiLeagueResponse.DatosPais paisData = new ApiLeagueResponse.DatosPais();
        paisData.setNombre("Inglaterra");

        ApiLeagueResponse.DatosResponse responseItem = new ApiLeagueResponse.DatosResponse();
        responseItem.setLiga(ligaData);
        responseItem.setPais(paisData);

        ApiLeagueResponse mockResponse = new ApiLeagueResponse();
        mockResponse.setResponse(List.of(responseItem));

        when(externalClient.obtenerLigaPorIdYTemporada(anyInt(), anyInt())).thenReturn(mockResponse);

        when(apiRepository.save(any(ApiModel.class))).thenReturn(new ApiModel());

        ApiEjecucionResponse resultado = apiService.procesarLigas();

        assertThat(resultado).isNotNull();
        assertThat(resultado.getEndpoint()).isEqualTo("/leagues");
        
        verify(localClient, times(7)).enviarLigaALocal(any(LigaRequest.class));
        
        verify(apiRepository, times(1)).save(any(ApiModel.class));
    }

    @Test
    @DisplayName("Debe registrar un estado FALLIDO en la BD si la API externa lanza error")
    void procesarLigas_CuandoApiFalla_RegistraFalloEnBd() {
        when(externalClient.obtenerLigaPorIdYTemporada(anyInt(), anyInt()))
            .thenThrow(new RuntimeException("API Sports Down"));

        apiService.procesarLigas();

        verify(apiRepository, times(8)).save(any(ApiModel.class));
        
        verify(localClient, never()).enviarLigaALocal(any());
    }
}
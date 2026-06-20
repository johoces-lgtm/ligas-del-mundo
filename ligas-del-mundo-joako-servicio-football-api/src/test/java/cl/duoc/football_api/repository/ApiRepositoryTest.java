package cl.duoc.football_api.repository;

import cl.duoc.football_api.model.ApiModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ApiRepositoryTest {

    @Autowired
    private ApiRepository apiRepository;

    private ApiModel logAuditoria;

    @BeforeEach
    void setUp() {
        logAuditoria = new ApiModel();
        logAuditoria.setEndpointConsultado("/fixtures");
        logAuditoria.setFechaEjecucion(LocalDateTime.now());
        logAuditoria.setEstado("EXITOSO");
        logAuditoria.setObservacion("Sincronización de partidos completada");
        logAuditoria.setRegistrosProcesados(380);
    }

    @Test
    @DisplayName("Debe guardar un log de auditoría y generar el ID automático")
    void guardarLog_GuardaCorrectamente() {
        ApiModel guardado = apiRepository.save(logAuditoria);
        
        Optional<ApiModel> resultadoBuscado = apiRepository.findById(guardado.getId());

        assertThat(resultadoBuscado).isPresent();
        assertThat(resultadoBuscado.get().getId()).isPositive();
        assertThat(resultadoBuscado.get().getEstado()).isEqualTo("EXITOSO");
        assertThat(resultadoBuscado.get().getRegistrosProcesados()).isEqualTo(380);
    }
}
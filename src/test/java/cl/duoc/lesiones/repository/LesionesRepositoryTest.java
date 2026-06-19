package cl.duoc.lesiones.repository;

import cl.duoc.lesiones.model.LesionesModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class LesionesRepositoryTest {

    @Autowired
    private LesionesRepository lesionesRepository;

    private LesionesModel lesionBase;

    @BeforeEach
    void setUp() {
        lesionBase = new LesionesModel();
        lesionBase.setJugadorId(154L); 
        lesionBase.setTipoLesion("Desgarro Isquiotibial");
        lesionBase.setGravedad("Moderada");
        lesionBase.setFechaInicio(LocalDate.now());
        lesionBase.setFechaEstimadaRecuperacion(LocalDate.now().plusDays(21));
    }

    @Test
    @DisplayName("Debe guardar una lesión y asignarle un ID autogenerado")
    void guardarLesion_CuandoDatosValidos_AsignaIdYGuarda() {
        var lesionGuardada = lesionesRepository.save(lesionBase);

        assertThat(lesionGuardada).isNotNull();
        assertThat(lesionGuardada.getId()).isPositive();
        assertThat(lesionGuardada.getTipoLesion()).isEqualTo("Desgarro Isquiotibial");
        assertThat(lesionGuardada.getFechaInicio()).isNotNull();
    }

    @Test
    @DisplayName("Debe retornar historial de lesiones buscando por ID del Jugador")
    void buscarPorJugadorId_CuandoExistenLesiones_RetornaLista() {
        lesionesRepository.save(lesionBase);
        
        var lesion2 = new LesionesModel();
        lesion2.setJugadorId(154L);
        lesion2.setTipoLesion("Esguince de Tobillo");
        lesion2.setGravedad("Leve");
        lesion2.setFechaInicio(LocalDate.now());
        lesionesRepository.save(lesion2);

        List<LesionesModel> historial = lesionesRepository.findByJugadorId(154L);

        assertThat(historial).hasSize(2);
        assertThat(historial).extracting(LesionesModel::getTipoLesion)
                .containsExactlyInAnyOrder("Desgarro Isquiotibial", "Esguince de Tobillo");
    }
}
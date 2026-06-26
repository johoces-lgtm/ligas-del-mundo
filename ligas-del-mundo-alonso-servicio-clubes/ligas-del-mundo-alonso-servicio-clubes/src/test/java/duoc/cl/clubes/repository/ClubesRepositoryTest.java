package duoc.cl.clubes.repository;

import static org.assertj.core.api.Assertions.assertThat; //

import java.util.List; //

import org.junit.jupiter.api.Test; //
import org.springframework.beans.factory.annotation.Autowired; //
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles; //

import duoc.cl.clubes.model.ClubesModel;

@DataJpaTest
@ActiveProfiles("test") 
class ClubesRepositoryTest {

    @Autowired 
    private ClubesRepository clubesRepository;

    @Test
    void findByLigaId_debeBuscarClubesPorSuLiga() {
        ClubesModel club = new ClubesModel(1L, "Colo-Colo", "http://logo.com/cc.png", 1925, "Estadio Monumental", 10L);
        clubesRepository.save(club);

        List<ClubesModel> resultado = clubesRepository.findByLigaId(10L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Colo-Colo");
        assertThat(resultado.get(0).getLigaId()).isEqualTo(10L);
    }
}
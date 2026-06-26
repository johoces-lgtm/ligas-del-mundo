package duoc.cl.paises.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import duoc.cl.paises.model.PaisModel;

@DataJpaTest
@ActiveProfiles("test")
class PaisesRepositoryTest {

    @Autowired
    private PaisRepository paisRepository;

    @Test
    void save_debeGuardarPaisCorrectamente() {
        PaisModel pais = new PaisModel();
        pais.setNombre("Chile");
        pais.setCodigoIso("CHI");
        pais.setUrlBandera("https://bandera.cl");

        PaisModel guardado = paisRepository.save(pais);

        assertThat(guardado.getId()).isEqualTo(1L);
        assertThat(guardado.getNombre()).isEqualTo("Chile");
    }
}
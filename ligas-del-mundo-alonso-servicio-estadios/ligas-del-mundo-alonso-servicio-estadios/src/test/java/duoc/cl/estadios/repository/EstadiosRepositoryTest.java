package duoc.cl.estadios.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import duoc.cl.estadios.model.EstadioModel;

@DataJpaTest
@ActiveProfiles("test")
class EstadiosRepositoryTest {

    @Autowired
    private EstadioRepository estadioRepository;

    @Test
    void save_debeGuardarEstadioCorrectamente() {
        EstadioModel estadio = new EstadioModel();
        estadio.setNombre("Nacional");
        estadio.setCapacidad(45000);
        estadio.setIdPais(5L);

        EstadioModel guardado = estadioRepository.save(estadio);

        assertThat(guardado).isNotNull();
        assertThat(guardado.getId()).isEqualTo(1L); 
        assertThat(guardado.getNombre()).isEqualTo("Nacional");
        assertThat(guardado.getCapacidad()).isEqualTo(45000);
    }
}
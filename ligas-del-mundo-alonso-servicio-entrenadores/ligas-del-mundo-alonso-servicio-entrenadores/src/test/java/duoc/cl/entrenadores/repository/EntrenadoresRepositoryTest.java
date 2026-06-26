package duoc.cl.entrenadores.repository;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import duoc.cl.entrenadores.model.EntrenadorModel;

@DataJpaTest
@ActiveProfiles("test")
class EntrenadoresRepositoryTest {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Test
    void findById_debeRetornarEntrenadorSiExiste() {
        EntrenadorModel entrenador = new EntrenadorModel();
        entrenador.setNombre("Gareca");
        entrenador.setNacionalidad("Argentina");
        entrenador.setIdClub(10L);
        
        entrenadorRepository.save(entrenador);

        boolean existe = entrenadorRepository.existsById(1L);

        assertThat(existe).isTrue();
    }
}
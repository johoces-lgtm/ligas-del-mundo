package cl.duoc.ligas.repository;

import cl.duoc.ligas.model.LigasModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class LigasRepositoryTest {

    @Autowired
    private LigasRepository ligasrepository; 

    private LigasModel ligaBase;

    @BeforeEach
    void setUp() {
        ligaBase = new LigasModel();
        ligaBase.setId(39L); 
        ligaBase.setNombre("Premier League");
        ligaBase.setPais("Inglaterra");
        ligaBase.setLogoUrl("https://example.com/premier.png");
    }

    @Test
    @DisplayName("Debe guardar una Liga correctamente con su ID manual")
    void guardarLiga_CuandoDatosSonValidos_GuardaExitosamente() {
            LigasModel ligaGuardada = ligasrepository.save(ligaBase);

        assertThat(ligaGuardada).isNotNull();
        assertThat(ligaGuardada.getId()).isEqualTo(39L);
        assertThat(ligaGuardada.getNombre()).isEqualTo("Premier League");
    }

    @Test
    @DisplayName("Debe encontrar una Liga existente por su ID")
    void buscarPorId_CuandoLigaExiste_RetornaLiga() {
        ligasrepository.save(ligaBase);

        Optional<LigasModel> resultado = ligasrepository.findById(39L); 
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getPais()).isEqualTo("Inglaterra");
    }
}
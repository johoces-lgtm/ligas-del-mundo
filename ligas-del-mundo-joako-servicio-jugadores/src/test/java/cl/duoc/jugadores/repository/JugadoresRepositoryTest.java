package cl.duoc.jugadores.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import cl.duoc.jugadores.model.JugadoresModel;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class JugadoresRepositoryTest {

    @Autowired
    private JugadoresRepository jugadoresRepository;

    private JugadoresModel jugadorBase;

    @BeforeEach
    void setUp() {
        jugadorBase = new JugadoresModel();
        
        jugadorBase.setId(1L); 
        
        jugadorBase.setNombre("Lionel Messi");
        jugadorBase.setNacionalidad("Argentina");
        jugadorBase.setPosicion("Delantero");
        jugadorBase.setEdad(38);
        jugadorBase.setFotoUrl("https://example.com/messi.jpg");
        jugadorBase.setClubId(10L); 

        jugadoresRepository.save(jugadorBase);
    }

    @Test
    void encontrarPorId_CuandoJugadorExiste_RetornaJugador() {
        Long idBuscado = 1L;

        var resultado = jugadoresRepository.findById(idBuscado);

        org.assertj.core.api.Assertions.assertThat(resultado).isPresent();
        org.assertj.core.api.Assertions.assertThat(resultado.get().getNombre()).isEqualTo("Lionel Messi");
        org.assertj.core.api.Assertions.assertThat(resultado.get().getClubId()).isEqualTo(10L);
    }
}
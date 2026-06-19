package cl.duoc.lesiones;

import org.junit.jupiter.api.Test; // Marca metodos como pruebas JUnit.
import org.springframework.boot.test.context.SpringBootTest; // Carga el contexto completo de Spring Boot.
import org.springframework.test.context.ActiveProfiles; // Permite activar el perfil test.
 
@SpringBootTest // Inicia la aplicacion completa para validar el contexto.
@ActiveProfiles("test") 
class LesionesApplicationTests {

	@Test
	void contextLoads() {
	}

}

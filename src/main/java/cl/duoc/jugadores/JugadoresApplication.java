package cl.duoc.jugadores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JugadoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(JugadoresApplication.class, args);
	}

}

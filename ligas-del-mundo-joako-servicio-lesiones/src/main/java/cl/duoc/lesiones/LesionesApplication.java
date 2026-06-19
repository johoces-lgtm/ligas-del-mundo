package cl.duoc.lesiones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LesionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LesionesApplication.class, args);
	}

}

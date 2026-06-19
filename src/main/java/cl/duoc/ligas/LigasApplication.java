package cl.duoc.ligas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LigasApplication {

	public static void main(String[] args) {
		SpringApplication.run(LigasApplication.class, args);
	}

}

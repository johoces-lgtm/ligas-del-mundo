package duoc.cl.estadios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EstadiosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstadiosApplication.class, args);
	}

}

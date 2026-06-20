package duoc.cl.paises;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaisesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaisesApplication.class, args);
    }

}
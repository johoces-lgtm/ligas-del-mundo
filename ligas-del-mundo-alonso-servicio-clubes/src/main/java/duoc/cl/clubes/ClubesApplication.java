package duoc.cl.clubes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ClubesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubesApplication.class, args);
    }

}
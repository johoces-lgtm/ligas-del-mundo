package com.example.posiciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PosicionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosicionesApplication.class, args);
    }
}
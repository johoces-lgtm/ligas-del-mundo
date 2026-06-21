package com.example.partidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PartidosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartidosApplication.class, args);
    }
}
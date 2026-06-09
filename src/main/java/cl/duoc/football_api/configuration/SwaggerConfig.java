package cl.duoc.football_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
            .info(new Info()
        .title("Football API - Ligas del Mundo")
        .version("1.0")
        .description("API que gestionará la información de clubes, estadios, países y entrenadores de las 7 principales ligas del mundo (Premier League, La Liga, Bundesliga, Serie A, Ligue 1, MLS y Saudí Pro League). Es información de las últimas 3 temporadas de cada liga. Toda esta información será obtenida a través de una API externa y almacenada en una base de datos local para su posterior consulta por parte de los clientes autorizados."))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                            new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"))
        );   
    }
}

package cl.duoc.ligas.configuration;

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
        .title("API Ligas - Ligas del Mundo")
        .version("1.0")
        .description("Api que gestiona datos de las 7 ligas más importantes del mundo (Premier League, La Liga, Serie A, Bundesliga, Ligue 1, MLS y Saudi Professional League), datos obtenidos desde la API-Sports. Permite realizar operaciones CRUD sobre las ligas almacenadas en la base de datos."))
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

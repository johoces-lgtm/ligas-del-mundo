package cl.duoc.jugadores.configuration;

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
        .title("API Jugadores")
        .version("1.0")
        .description("Api que gestiona los datos de jugadores de las 7 ligas más importantes del mundo (Premier League, La Liga, Serie A, Bundesliga, Ligue 1, MLS y Saudí Pro League). Permite realizar operaciones CRUD sobre los jugadores, incluyendo su nombre, nacionalidad, posición, edad, foto y el club al que pertenecen. Además, la API está protegida mediante JWT para garantizar la seguridad de los datos."))
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

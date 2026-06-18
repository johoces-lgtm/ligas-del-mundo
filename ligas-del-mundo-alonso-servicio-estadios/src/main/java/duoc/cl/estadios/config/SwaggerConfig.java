package duoc.cl.estadios.config;

import java.beans.BeanProperty;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
                .info(new Info()
                        .title("API de Estadios")
                        .version("1.0")
                        .description("Api que gestiona los datos de estadios de las 7 ligas más importantes del mundo (Premier League, La Liga, Serie A, Bundesliga, Ligue 1, MLS y Saudí Pro League)."))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}

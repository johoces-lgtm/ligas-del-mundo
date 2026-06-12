package duoc.cl.clubes.config;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ligas del Mundo - Servicio Clubes API")
                        .version("1.0")
                        .description("API para gestionar clubes en el sistema de Ligas del Mundo"));
    }

}

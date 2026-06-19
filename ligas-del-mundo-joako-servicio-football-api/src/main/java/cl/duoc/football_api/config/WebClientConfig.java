package cl.duoc.football_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import cl.duoc.football_api.service.JwtService;

@Configuration
public class WebClientConfig {

    @Value("${api.football.url}")
    private String apiUrl;

    @Value("${api.football.key}")
    private String apiKey;

    private final JwtService jwtService;

    // Inyectamos nuestro servicio de JWT mediante el constructor
    public WebClientConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean(name = "apiFootballWebClient")
    public WebClient apiFootballWebClient() {
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl(apiUrl)
                // Cabeceras exclusivas para la API externa
                .defaultHeader("x-rapidapi-key", apiKey)
                .defaultHeader("x-rapidapi-host", "v3.football.api-sports.io")
                .build();
    }

    @Bean(name = "internalWebClient")
    public WebClient.Builder internalWebClientBuilder() {
        // Agregamos el interceptor de seguridad SOLO al tráfico interno
        return WebClient.builder()
                .filter(inyectarTokenAutenticacion());
    }

    // Interceptor que "fabrica" y pega el token JWT en cada petición local
    private ExchangeFilterFunction inyectarTokenAutenticacion() {
        return (request, next) -> {
            // Generamos el pase VIP del orquestador
            String token = jwtService.generarTokenSistema();
            
            // Clonamos la petición original y le inyectamos la cabecera Authorization
            ClientRequest nuevaPeticion = ClientRequest.from(request)
                    .header("Authorization", "Bearer " + token)
                    .build();
            
            return next.exchange(nuevaPeticion);
        };
    }
}
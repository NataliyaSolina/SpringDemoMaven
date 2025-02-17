package ru.examples.springdemo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

//http://localhost:8080/swagger-ui/index.html
@Configuration
public class OpenApiConfig {

    private Environment environment;

    //    private final String url = environment.getProperty("api.server.url");
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Todo Service - сервис списка дел")
                        .description("Это API предоставляет эндпоинты для управления задачами пользователей")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Nataliya Solina")
                                .email("livegrafics@mail.ru")))
                .servers(List.of(new Server()
                        .url("http://localhost:8080")
                        .description("Development")));
    }
}
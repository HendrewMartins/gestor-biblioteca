package br.hendrew.gestor_biblioteca.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestor Biblioteca")
                        .version("1.0.0")
                        .description("Documentação da API do gestor de biblioteca")
                        .contact(new Contact()
                                .name("Hendrew Felipe Martins")
                                .email("hendrewmartins@hotmail.com")
                        ));
    }
}

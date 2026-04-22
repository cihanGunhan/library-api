package com.cihangunhan.libraryapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Personal Library API")
                        .version("1.0.0")
                        .description("Kişisel kitap koleksiyonu yönetim API'si")
                        .contact(new Contact()
                                .name("Cihan C. Günhan")
                                .email("cihancgunhan@gmail.com")));
    }
}
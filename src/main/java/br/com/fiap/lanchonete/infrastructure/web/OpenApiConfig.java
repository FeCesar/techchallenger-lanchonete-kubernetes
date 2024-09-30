package br.com.fiap.lanchonete.infrastructure.web;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi clienteApi() {
        String[] paths = {"/lanchonete/v1/**"};
        return GroupedOpenApi.builder()
                .group("groups")
                .pathsToMatch(paths)
                .build();
    }
}
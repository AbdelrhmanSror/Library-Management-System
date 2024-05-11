package com.example.librarymanagementsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("LMS")
                .pathsToMatch("/public/**")
                .build();
    }

    @Bean
    public OpenAPI EDFOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("LMS API")
                        .description("Library Management System sample application")
                        .version("v0.0.1"));
    }

}
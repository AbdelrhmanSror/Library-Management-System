package com.example.librarymanagementsystem.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("LMS")
                .pathsToMatch("/public/**")
                .build();
    }

    @Bean
    public OpenAPI LMSOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("LMS API")
                        .description("Library Management System sample application")
                        .version("v0.0.1")).addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

}
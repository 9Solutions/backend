package com.caixadesapato.api.config.swagger;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@Configuration
public class Swagger {
    @GetMapping("/")
    public void redirectSwagger(
            HttpServletRequest request, HttpServletResponse response
    ) {
        String url = "swagger-ui/index.html#/";
        response.setHeader("Location", url);
        response.setStatus(302);
    }

    @Bean
    public OpenAPI customAPI() {
        return new OpenAPI().info(
                new Info().title("API - Projeto Caixa de Sapato")
                        .version("2.0.0")
                        .description("API construída com o objetivo de melhorar o processo de doações da ONG Caixa de Sapato")
                        .license(
                                new License().name("Projeto Caixa de Sapato ©")
                                        .url("www.projetocaixadesapato.org/")
                        )

        );
    }
}

package com.desafio.builders.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.desafio.builders.controller"))
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(this.informacoesApi().build());
    }

    private ApiInfoBuilder informacoesApi() {

        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();

        apiInfoBuilder.title("SPRING REST API");
        apiInfoBuilder.description("Documentação das APIs REST");
        apiInfoBuilder.version("1.0");
        apiInfoBuilder.contact(this.contato());

        return apiInfoBuilder;

    }

    private Contact contato() {
        return new Contact(
                "Jonatas Edward",
                "jonataseduard23@gmail.com",
                "62-98328-9988");
    }
}

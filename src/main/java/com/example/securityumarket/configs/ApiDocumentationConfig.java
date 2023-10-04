package com.example.securityumarket.configs;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class ApiDocumentationConfig {

    @Value("${swagger.value.dev-url}")
    private String devUrl;

    @Value("${swagger.value.prod-url}")
    private String prodUrl;

    //TODO refactor all text need to off
    @Bean
    public OpenAPI uAutoAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production");


        Contact contact = new Contact();
        contact.setName("Team challenge");
        contact.setUrl("https://github.com/TeamChallenge-uMarket/backend");


        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("uAuto API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage.").termsOfService("https://github.com/TeamChallenge-uMarket/backend")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
package com.example.securityumarket;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestBean {

    @ServiceConnection
    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer(DynamicPropertyRegistry registry) {
        var container = new PostgreSQLContainer<>("postgres:16.0");
        registry.add("postgresql.driver", container::getDriverClassName);
        return container;
    }
}

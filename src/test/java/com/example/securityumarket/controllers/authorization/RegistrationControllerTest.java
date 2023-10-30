package com.example.securityumarket.controllers.authorization;

import com.example.securityumarket.models.DTO.login_page.RegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@RunWith(SpringRunner.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
class RegistrationControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private static final String URL = "/api/v1/authorization/register";

    @Container
    @ServiceConnection
    private final static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Test
    void connectionEstablished() {
        Assertions.assertTrue(postgres.isCreated());
        Assertions.assertTrue(postgres.isRunning());
    }

    @Test
    @Rollback
    void validDataRegister_Ok() {
        String url = "http://localhost:" + port + URL;
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("Dmytro");
        registerRequest.setEmail("dmytro@gmail.com");
        registerRequest.setPassword("ABCDE12345");
        registerRequest.setConfirmPassword("ABCDE12345");
        ResponseEntity<String> response = restTemplate.postForEntity(url, registerRequest, String.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @Rollback
    void duplicateDataRegister_Failure() {
        String url = "http://localhost:" + port + URL;
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("Dmytro");
        registerRequest.setEmail("dmytro@gmail.com");
        registerRequest.setPassword("ABCDE12345");
        registerRequest.setConfirmPassword("ABCDE12345");
        restTemplate.postForEntity(url, registerRequest, String.class);
        ResponseEntity<String> response = restTemplate.postForEntity(url, registerRequest, String.class);
        Assertions.assertEquals(409, response.getStatusCode().value());
    }

    @Test
    void invalidDataRegister_Failure() {
        String url = "http://localhost:" + port + URL;
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("Dmytro");
        registerRequest.setEmail("dmytro@gmail.com");
        registerRequest.setPassword("12345");
        registerRequest.setConfirmPassword("12345");
        ResponseEntity<String> response = restTemplate.postForEntity(url, registerRequest, String.class);
        Assertions.assertEquals(422, response.getStatusCode().value());
    }
}
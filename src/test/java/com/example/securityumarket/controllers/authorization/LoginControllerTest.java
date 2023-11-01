package com.example.securityumarket.controllers.authorization;

import com.example.securityumarket.models.authentication.AuthenticationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@RunWith(SpringRunner.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
class LoginControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String LOGIN_URL = "/api/v1/authorization/login";

    @Container
    @ServiceConnection
    private final static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");


    @Test
    void connectionEstablished() {
        Assertions.assertTrue(postgres.isCreated());
        Assertions.assertTrue(postgres.isRunning());
    }

    @Sql(value = {"add_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"delete_users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void loginWithValidCredentials_Ok() {
        String url = "http://localhost:" + port + LOGIN_URL;
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("dmytro@gmail.com");
        authenticationRequest.setPassword("ABCDE12345");
        ResponseEntity<String> response = restTemplate.postForEntity(url, authenticationRequest, String.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Sql(value = {"add_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"delete_users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void loginWithInvalidCredentials_Failure() {
        String url = "http://localhost:" + port + LOGIN_URL;
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setEmail("dmytro@gmail.com");
        authenticationRequest.setPassword("wrong_password");
        ResponseEntity<String> response = restTemplate.postForEntity(url, authenticationRequest, String.class);
        Assertions.assertEquals(403, response.getStatusCode().value());
    }

}
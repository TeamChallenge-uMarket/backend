package com.example.securityumarket.controllers.authorization;

import com.example.securityumarket.models.DTO.login_page.PasswordRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
class ResetPasswordControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${localhost.url}")
    private String LOCALHOST_URL;

    @Value("${reset-password.url}")
    private String RESET_PASSWORD_URL;

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
    public void sendCode_Ok() {
        String email = "dmytro@gmail.com";
        String url = LOCALHOST_URL + port + RESET_PASSWORD_URL + "/send-code" + "?email=" + email;
        HttpEntity<String> requestEntity = new HttpEntity<>(email);

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            requestEntity,
            String.class
        );
        Assertions.assertEquals(200, response.getStatusCode().value());
    }


    @Sql(value = {"add_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"delete_users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void sendCodeNonExistentUser_Failure() {
        String email = "petro@gmail.com";
        String url = LOCALHOST_URL + port + RESET_PASSWORD_URL + "/send-code" + "?email=" + email;
        HttpEntity<String> requestEntity = new HttpEntity<>(email);

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.PUT,
            requestEntity,
            String.class
        );
        Assertions.assertEquals(404, response.getStatusCode().value());
    }

    @Sql(value = {"add_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"delete_users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void resetPassword_Ok() {
        String url = LOCALHOST_URL + port + RESET_PASSWORD_URL;

        PasswordRequest passwordRequest = new PasswordRequest();
        passwordRequest.setEmail("dmytro@gmail.com");
        passwordRequest.setPassword("ABCDE12345");
        passwordRequest.setConfirmPassword("ABCDE12345");
        ResponseEntity<String> response = restTemplate.postForEntity(url, passwordRequest, String.class);
        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Sql(value = {"add_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"delete_users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void resetPasswordWeakPassword_Failure() {
        String url = LOCALHOST_URL + port + RESET_PASSWORD_URL;

        PasswordRequest passwordRequest = new PasswordRequest();
        passwordRequest.setEmail("dmytro@gmail.com");
        passwordRequest.setPassword("password");
        passwordRequest.setConfirmPassword("password");
        ResponseEntity<String> response = restTemplate.postForEntity(url, passwordRequest, String.class);
        Assertions.assertEquals(422, response.getStatusCode().value());
    }

    @Sql(value = {"add_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"delete_users.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void resetPasswordNonExistentEmail_Failure() {
        String url = LOCALHOST_URL + port + RESET_PASSWORD_URL;

        PasswordRequest passwordRequest = new PasswordRequest();
        passwordRequest.setEmail("petro@gmail.com");
        passwordRequest.setPassword("ABCDE12345");
        passwordRequest.setConfirmPassword("ABCDE12345");
        ResponseEntity<String> response = restTemplate.postForEntity(url, passwordRequest, String.class);
        Assertions.assertEquals(404, response.getStatusCode().value());
    }
}
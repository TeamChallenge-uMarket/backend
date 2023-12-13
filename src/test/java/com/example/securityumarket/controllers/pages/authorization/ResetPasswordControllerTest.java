package com.example.securityumarket.controllers.pages.authorization;

import com.example.securityumarket.TestBean;
import com.example.securityumarket.models.DTO.pages.login.PasswordRequest;
import com.example.securityumarket.models.DTO.pages.login.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = TestBean.class)
class ResetPasswordControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Value("${reset-password.url}")
    private String RESET_PASSWORD_URL;

    @Transactional
    @Sql(value = {"add_active_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @ValueSource(strings = {"tempmail@gmail.com"})
    @ParameterizedTest
    public void testSendCode(String email) throws Exception {
        mockMvc.perform(put(RESET_PASSWORD_URL + "/send-code")
                        .param("email", String.valueOf(email)))
                .andExpect(status().isOk());
    }

    @ValueSource(strings = {"nonexistent@gmail.com"})
    @ParameterizedTest
    public void testSendCode_NotFound(String email) throws Exception {
        mockMvc.perform(put(RESET_PASSWORD_URL + "/send-code")
                        .param("email", String.valueOf(email)))
                .andExpect(status().isNotFound());
    }


    @Transactional
    @Sql(value = {"add_active_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void testResetPassword() throws Exception {
        PasswordRequest passwordRequest = new RegisterRequest();
        passwordRequest.setEmail("tempmail@gmail.com");
        passwordRequest.setPassword("password11");
        passwordRequest.setConfirmPassword("password11");
        mockMvc.perform(post(RESET_PASSWORD_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(passwordRequest)))
                .andExpect(status().isOk());
    }

    @Transactional
    @Test
    public void testResetPassword_NotFound() throws Exception {
        PasswordRequest passwordRequest = new RegisterRequest();
        passwordRequest.setEmail("tempmail@gmail.com");
        passwordRequest.setPassword("password11");
        passwordRequest.setConfirmPassword("password11");
        mockMvc.perform(post(RESET_PASSWORD_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(passwordRequest)))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Sql(value = {"add_active_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void testResetPassword_UnprocessableEntity() throws Exception {
        PasswordRequest passwordRequest = new RegisterRequest();
        passwordRequest.setEmail("tempmail@gmail.com");
        passwordRequest.setPassword("wrongpassword");
        passwordRequest.setConfirmPassword("wrongpassword");
        mockMvc.perform(post(RESET_PASSWORD_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(passwordRequest)))
                .andExpect(status().isUnprocessableEntity());
    }

    private static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
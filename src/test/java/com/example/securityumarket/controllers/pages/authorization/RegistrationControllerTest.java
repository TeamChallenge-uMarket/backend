package com.example.securityumarket.controllers.pages.authorization;

import com.example.securityumarket.TestBean;
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
class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Value("${register.url}")
    private String REGISTER_URL;

    @Transactional
    @Test
    void testRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("temp");
        registerRequest.setEmail("tempmail@gmail.com");
        registerRequest.setPassword("ABCDE12345");
        registerRequest.setConfirmPassword("ABCDE12345");
        mockMvc.perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void duplicateDataRegister_Duplicate() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("Oleh");
        registerRequest.setEmail("praice07@gmail.com");
        registerRequest.setPassword("ABCDE12345");
        registerRequest.setConfirmPassword("ABCDE12345");
        mockMvc.perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void invalidDataRegister_UnprocessableEntity() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("temp");
        registerRequest.setEmail("tempmail@gmail.com");
        registerRequest.setPassword("12345");
        registerRequest.setConfirmPassword("12345");
        mockMvc.perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Transactional
    @Sql(value = {"add_inactive_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @ValueSource(strings = {"tempmail@gmail.com"})
    @ParameterizedTest
    void testResendCode(String email) throws Exception {
        mockMvc.perform(put(REGISTER_URL + "/resend-code")
                        .param("email", String.valueOf(email)))
                .andExpect(status().isOk());
    }

    @Transactional
    @Sql(value = {"add_active_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @ValueSource(strings = {"tempmail@gmail.com"})
    @ParameterizedTest
    void testResendCode_UnprocessableEntity(String email) throws Exception {
        mockMvc.perform(put(REGISTER_URL + "/resend-code")
                        .param("email", String.valueOf(email)))
                .andExpect(status().isUnprocessableEntity());
    }

    @ValueSource(strings = {"nonexistent@gmail.com"})
    @ParameterizedTest
    void testResendCode_NotFound(String email) throws Exception {
        mockMvc.perform(put(REGISTER_URL + "/resend-code")
                        .param("email", String.valueOf(email)))
                .andExpect(status().isNotFound());
    }

    private static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
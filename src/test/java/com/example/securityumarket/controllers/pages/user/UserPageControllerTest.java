package com.example.securityumarket.controllers.pages.user;

import com.example.securityumarket.TestBean;
import com.example.securityumarket.dto.entities.user.UserDetailsDTO;
import com.example.securityumarket.dto.entities.user.UserSecurityDetailsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = TestBean.class)
@WithUserDetails(value = "praice07@gmail.com", userDetailsServiceBeanName = "userDetailsServiceImpl")
public class UserPageControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    protected static MockMvc mockMvc;

    @Value("${user-page.url}")
    private String USER_PAGE_URL;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders.
            webAppContextSetup(applicationContext)
            .apply(springSecurity())
            .build();
    }


    @Test
    void updateUserDetailsTest() throws Exception {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setPhone("+380999999999");
        InputStream stream = Thread.currentThread().getContextClassLoader()
            .getResourceAsStream("com/example/securityumarket/controllers/pages/user/sample-photo.png");
        MockMultipartFile photoMultipartFile = new MockMultipartFile(
            "multipartFile",
            "sample-photo.png",
            "image/png",
            stream
        );

        String userDetailsJson = objectMapper.writeValueAsString(userDetailsDTO);

        MockMultipartFile filepart = new MockMultipartFile("body", "", "application/json", userDetailsJson.getBytes());
        mockMvc.perform(multipart(USER_PAGE_URL)
                .file(photoMultipartFile)
                .file(filepart).characterEncoding("UTF_8")
                .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
            .andExpect(status().isOk());


    }

    @Test
    void deleteUserPhotoTest() throws Exception {
        var res = mockMvc.perform(delete(USER_PAGE_URL + "/delete-photo"))
            .andExpect(status().isOk()).andReturn();
        Assertions.assertNotNull(res);
    }
    
    @Test
    @Sql(value = "add_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithUserDetails(value = "temp@tempmail.com", userDetailsServiceBeanName = "userDetailsServiceImpl")
    void updateSecurityInformationTest() throws Exception {
        UserSecurityDetailsDTO dto = new UserSecurityDetailsDTO();
        dto.setEmail("temp@tempmail.com");
        dto.setOldPassword("ABCDE12345");
        dto.setPassword("ABCDE12346");
        dto.setConfirmPassword("ABCDE12346");
        
        var res = mockMvc.perform(put(USER_PAGE_URL + "/security-info")
                .content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();
        Assertions.assertNotNull(res);
    }

    @Test
    @WithMockUser(username = "test.email@example.com", password = "ABCDE12345")
    void updateSecurityInformationTest_WrongPassword_Failure() throws Exception {
        UserSecurityDetailsDTO dto = new UserSecurityDetailsDTO();
        dto.setEmail("test.email@example.com");
        dto.setOldPassword("ABCDE12345");
        dto.setPassword("ABCDE12346");
        dto.setConfirmPassword("ABCDE12346");

        var res = mockMvc.perform(put(USER_PAGE_URL + "/security-info")
                .content(objectMapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnprocessableEntity()).andReturn();
        Assertions.assertNotNull(res);
    }

    @Test
    void getMyTransportsTest() throws Exception {
        var res = mockMvc.perform(get(USER_PAGE_URL + "/my-transports/ACTIVE"))
            .andExpect(status().isOk()).andReturn();
        Assertions.assertNotNull(res);
    }

    @Test
    void updateTransportStatus() throws Exception {
        var res = mockMvc.perform(put(USER_PAGE_URL + "/my-transports/{transport-id}/update-status/{status}", 1, "PENDING"))
            .andExpect(status().isOk()).andReturn();
        Assertions.assertNotNull(res);
    }

    @Test
    void getTransportDetails() throws Exception {
        MvcResult res = mockMvc.perform(get(USER_PAGE_URL + "/my-transports/get-details/{transport-id}", 1))
            .andExpect(status().isOk()).andReturn();
        Assertions.assertNotNull(res);
    }

    @Test
    void updateTransportDetails() throws Exception {

        InputStream stream = Thread.currentThread().getContextClassLoader()
            .getResourceAsStream("com/example/securityumarket/controllers/pages/user/sample-photo.png");
        MockMultipartFile photoMultipartFile = new MockMultipartFile(
            "multipartFile",
            "sample-photo.png",
            "image/png",
            stream
        );

        var res = mockMvc.perform(multipart(USER_PAGE_URL + "/my-transports/update-details/{transport-id}", 1)
                .file(photoMultipartFile)
                .accept(MediaType.APPLICATION_JSON, MediaType.MULTIPART_FORM_DATA)
                .with(request -> {
                    request.setMethod("PUT");
                    return request;
                }))
            .andExpect(status().isOk());

        Assertions.assertNotNull(res);
    }
}

package com.example.securityumarket.controllers.pages.catalog;

import com.example.securityumarket.TestBean;
import com.example.securityumarket.models.DTO.catalog_page.request.RequestSearchDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = TestBean.class)
@WithUserDetails(value = "praice07@gmail.com", userDetailsServiceBeanName = "userDetailsServiceImpl")

public class CatalogControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    protected static MockMvc mockMvc;

    @Value("${catalog.url}")
    private String CATALOG_URL;

    @BeforeAll
    @Sql(value = {"add_transport.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders.
            webAppContextSetup(applicationContext)
            .apply(springSecurity())
            .build();
    }

    @AfterAll
    @Sql(value = {"delete_transport.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    static void afterAll() {

    }


    @Test
    void addFavoriteTest() throws Exception {
        MvcResult result = mockMvc.perform(put(CATALOG_URL + "/favorite-add/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        Assertions.assertNotNull(response);
    }

    @Test
    void removeFavouriteTest() throws Exception {
        MvcResult result = mockMvc.perform(delete(CATALOG_URL + "/favorite-remove/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();

        String response = result.getResponse().getContentAsString();
        Assertions.assertNotNull(response);
    }

    @Test
    void searchTransportTest() throws Exception {
        RequestSearchDTO searchDTO = RequestSearchDTO.builder().yearsFrom(2015).yearsTo(2019).build();

        MvcResult result = mockMvc.perform(get(CATALOG_URL + "/search/page/{page}/limit/{limit}/", 0, 10)
                .content(objectMapper.writeValueAsString(searchDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();

        List<RequestSearchDTO> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.isEmpty());
    }

    @Test
    void getFilterParametersTest() throws Exception {
        MvcResult result = mockMvc.perform(get(CATALOG_URL + "/get-param")
                .param("transportTypeId", "2")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        Assertions.assertNotNull(result.getResponse().getContentAsString());
    }
}

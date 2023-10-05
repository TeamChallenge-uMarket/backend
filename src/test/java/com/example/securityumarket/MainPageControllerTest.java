package com.example.securityumarket;

import com.example.securityumarket.controllers.main_page.MainPageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@Sql(value = {"/create_models_before.sql", "/create_cities_before.sql", "/create_user_before.sql", "/create_user_before.sql", "/create_cars_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_models_after.sql", "/create_cities_after.sql", "/create_user_after.sql", "/create_user_after.sql", "/create_cars_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource("/application-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainPageControllerTest {

    private static final String REST_URL = "/api/v1/main";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainPageController mainPageController;

    @Test
    public void mainPageControllerTest() throws Exception {
//        this.mockMvc.perform(get(REST_URL))
//                .andDo(print())
//                .andExpect(authenticated());
    }
}

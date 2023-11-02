package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.TestBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest(classes = TestBean.class)
class MainPageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Value("${main.url}")
    private String MAIN_URL;


    @Test
    void testGetTypeTransport() throws Exception {
        this.mockMvc.perform(get(MAIN_URL + "/types"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                [
                                     {
                                         "typeId": 1,
                                         "type": "Легкові"
                                     },
                                     {
                                         "typeId": 2,
                                         "type": "Мото"
                                     },
                                     {
                                         "typeId": 3,
                                         "type": "Вантажівки"
                                     },
                                     {
                                         "typeId": 4,
                                         "type": "Спецтехніка"
                                     },
                                     {
                                         "typeId": 5,
                                         "type": "Сільгосптехніка"
                                     },
                                     {
                                         "typeId": 6,
                                         "type": "Водний транспорт"
                                     }
                                 ]
                                """)
                );
    }

    @ValueSource(longs = {1L, 2L, 3L, 4L, 5L, 6L})
    @ParameterizedTest
    void testGetBrandsByTransportType(Long transportTypeId) throws Exception {
        this.mockMvc.perform(get(MAIN_URL + "/brands")
                .param("transportTypeId", String.valueOf(transportTypeId))
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpectAll(
                            status().isOk(),
                            content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                    );
    }

    @ValueSource(longs = {7L, 8L, 9L})
    @ParameterizedTest
    void testGetBrandsByTransportType_NotFound(Long transportTypeId) throws Exception {
        this.mockMvc.perform(get(MAIN_URL + "/brands")
                        .param("transportTypeId", String.valueOf(transportTypeId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
package com.example.securityumarket.controllers.main_page;

import com.example.securityumarket.TestBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @Test
    void testGetModelsByBrand() throws Exception {
        this.mockMvc.perform(get(MAIN_URL + "/models")
                        .param("transportBrandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        mockMvc.perform(get(MAIN_URL + "/models")
                        .param("transportBrandId", "1")
                        .param("transportTypeId", "invalid_type_id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get(MAIN_URL + "/models")
                        .param("transportBrandId", "122")
                        .param("transportTypeId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @CsvSource({
            "1, 1",
            "1, 2",
            "1, 3",
            "1, 10",
            "1, 40"
    })
    @ParameterizedTest
    void testGetModelsByBrand(Long transportTypeId, Long transportBrandId) throws Exception {
        mockMvc.perform(get(MAIN_URL + "/models")
                        .param("transportTypeId", String.valueOf(transportTypeId))
                        .param("transportBrandId", String.valueOf(transportBrandId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @CsvSource({
            "1,",
            "2,"
    })
    @ParameterizedTest
    void testGetModelsByBrand_BadRequest(Long transportTypeId, Long transportBrandId) throws Exception {
        mockMvc.perform(get(MAIN_URL + "/models")
                        .param("transportTypeId", String.valueOf(transportTypeId))
                        .param("transportBrandId", String.valueOf(transportBrandId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @CsvSource({
            "1, 100",
            "2, 30",
            "3, 30",
            "4, 30"
    })
    @ParameterizedTest
    void testGetModelsByBrand_NotFound(Long transportTypeId, Long transportBrandId) throws Exception {
        mockMvc.perform(get(MAIN_URL + "/models")
                        .param("transportTypeId", String.valueOf(transportTypeId))
                        .param("transportBrandId", String.valueOf(transportBrandId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
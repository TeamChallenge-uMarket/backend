package com.example.securityumarket.controllers.pages.main;

import com.example.securityumarket.TestBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        mockMvc.perform(get(MAIN_URL + "/brands")
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
        mockMvc.perform(get(MAIN_URL + "/brands")
                        .param("transportTypeId", String.valueOf(transportTypeId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetModelsByBrand() throws Exception {
        mockMvc.perform(get(MAIN_URL + "/models")
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

    @Test
    void testGetRegions() throws Exception {
        mockMvc.perform(get(MAIN_URL + "/regions"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                                
                                                [
                                    {
                                        "regionId": 1,
                                        "region": "Вінницька область"
                                    },
                                    {
                                        "regionId": 2,
                                        "region": "Волинська область"
                                    },
                                    {
                                        "regionId": 3,
                                        "region": "Дніпропетровська область"
                                    },
                                    {
                                        "regionId": 4,
                                        "region": "Донецька область"
                                    },
                                    {
                                        "regionId": 5,
                                        "region": "Житомирська область"
                                    },
                                    {
                                        "regionId": 6,
                                        "region": "Закарпатська область"
                                    },
                                    {
                                        "regionId": 7,
                                        "region": "Запорізька область"
                                    },
                                    {
                                        "regionId": 8,
                                        "region": "Івано-Франківська область"
                                    },
                                    {
                                        "regionId": 9,
                                        "region": "Київська область"
                                    },
                                    {
                                        "regionId": 10,
                                        "region": "Кіровоградська область"
                                    },
                                    {
                                        "regionId": 11,
                                        "region": "Луганська область"
                                    },
                                    {
                                        "regionId": 12,
                                        "region": "Львівська область"
                                    },
                                    {
                                        "regionId": 13,
                                        "region": "Миколаївська область"
                                    },
                                    {
                                        "regionId": 14,
                                        "region": "Одеська область"
                                    },
                                    {
                                        "regionId": 15,
                                        "region": "Полтавська область"
                                    },
                                    {
                                        "regionId": 16,
                                        "region": "Рівненська область"
                                    },
                                    {
                                        "regionId": 17,
                                        "region": "Сумська область"
                                    },
                                    {
                                        "regionId": 18,
                                        "region": "Тернопільська область"
                                    },
                                    {
                                        "regionId": 19,
                                        "region": "Харківська область"
                                    },
                                    {
                                        "regionId": 20,
                                        "region": "Херсонська область"
                                    },
                                    {
                                        "regionId": 21,
                                        "region": "Хмельницька область"
                                    },
                                    {
                                        "regionId": 22,
                                        "region": "Черкаська область"
                                    },
                                    {
                                        "regionId": 23,
                                        "region": "Чернівецька область"
                                    },
                                    {
                                        "regionId": 24,
                                        "region": "Чернігівська область"
                                    },
                                    {
                                        "regionId": 25,
                                        "region": "АР Крим"
                                    }
                                ]
                                """));
    }

    private static Stream<Arguments> regionIdProvider() {
        return Stream.of(
                Arguments.of(Collections.singletonList(1L)),
                Arguments.of(Arrays.asList(2L, 3L, 4L)),
                Arguments.of(Arrays.asList(3L, 5L))
        );
    }

    @MethodSource("regionIdProvider")
    @ParameterizedTest
    void testGetCitiesWithRegionId(List<Long> regionId) throws Exception {
        mockMvc.perform(get(MAIN_URL + "/cities")
                        .param("regionId", regionId.stream()
                                .map(String::valueOf)
                                .toArray(String[]::new))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCitiesWithoutRegionId() throws Exception {
        mockMvc.perform(get(MAIN_URL + "/cities"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(greaterThan(0)))
                );
    }


    @Transactional
    @Sql(value = {"add_transport.sql"})
    @Test
    void testGetNewTransports() throws Exception {
        mockMvc.perform(get(MAIN_URL + "/new-transports"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(greaterThan(0))),
                        jsonPath("$[0].brand", is("Toyota")),
                        jsonPath("$[0].model", is("Corolla")),
                        jsonPath("$[0].year", is(2018)),
                        jsonPath("$[0].transmission", is("Автомат")),
                        jsonPath("$[0].description", is("test-transport"))
                );
    }

    @Test
    void testGetPopularsTransports() throws Exception {
        mockMvc.perform(get(MAIN_URL + "/popular-transports"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(greaterThan(0)))
                );
    }

    @Sql(value = {"add_recently_view.sql"})
    @WithUserDetails(value = "praice07@gmail.com", userDetailsServiceBeanName = "userDetailsServiceImpl")
    @Test
    void testGetRecentlyViewedTransports() throws Exception {
        mockMvc.perform(get(MAIN_URL + "/recently-viewed"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(greaterThan(0))),
                        jsonPath("$[0].brand", is("Audi")),
                        jsonPath("$[0].model", is("A6")),
                        jsonPath("$[0].year", is(2018)),
                        jsonPath("$[0].transmission", is("Робот")),
                        jsonPath("$[0].description", is("description"))
                );
    }

    @Sql(value = {"add_recently_view.sql"})
    @WithUserDetails(value = "praice07@gmail.com", userDetailsServiceBeanName = "userDetailsServiceImpl")
    @Test
    void testGetFavorites() throws Exception {
        mockMvc.perform(get(MAIN_URL + "/favorite-transports"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(greaterThan(0))),
                        jsonPath("$[0].brand", is("Audi")),
                        jsonPath("$[0].model", is("A6")),
                        jsonPath("$[0].year", is(2012)),
                        jsonPath("$[0].transmission", is("Ручна/Механіка")),
                        jsonPath("$[0].description", is("description"))
                );
    }
}
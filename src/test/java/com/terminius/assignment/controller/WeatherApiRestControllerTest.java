package com.terminius.assignment.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terminius.assignment.dto.WeatherDto;
import com.terminius.assignment.repository.WeatherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by muhammad.junaid on 9/5/2021
 */
@SpringBootTest
@AutoConfigureMockMvc
class WeatherApiRestControllerTest {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final ObjectMapper om = new ObjectMapper();
    @Autowired
    WeatherRepository weatherRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addWeatherData() throws Exception{
        WeatherDto expectedRecord = getTestData().get("karachi");
        WeatherDto actualRecord = om.readValue(mockMvc.perform(post("/weather")
                .contentType("application/json")
                .content(om.writeValueAsString(getTestData().get("karachi"))))
                .andDo(print())
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), WeatherDto.class);

        assertTrue(new ReflectionEquals(expectedRecord, "id").matches(actualRecord));
        assertEquals(true, weatherRepository.findById(actualRecord.getId()).isPresent());
    }

    @Test
    void getWeatherData() throws Exception {
        Map<String, WeatherDto> data = getTestData();
        data.remove("karachi2");
        List<WeatherDto> expectedRecords = new ArrayList<>();

        for (Map.Entry<String, WeatherDto> kv : data.entrySet()) {
            expectedRecords.add(om.readValue(mockMvc.perform(post("/weather")
                    .contentType("application/json")
                    .content(om.writeValueAsString(kv.getValue())))
                    .andDo(print())
                    .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), WeatherDto.class));
        }
        Collections.sort(expectedRecords, Comparator.comparing(WeatherDto::getId));

        List<WeatherDto> actualRecords = om.readValue(mockMvc.perform(get("/weather"))
                .andDo(print())
                .andExpect(jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(jsonPath("$.*", hasSize(expectedRecords.size())))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), new TypeReference<List<WeatherDto>>() {
        });

        for (int i = 0; i < expectedRecords.size(); i++) {
            assertTrue(new ReflectionEquals(expectedRecords.get(i)).matches(actualRecords.get(i)));
        }
    }

    @Test
    void getWeatherDataById() throws Exception {
        WeatherDto expectedRecord = om.readValue(mockMvc.perform(post("/weather")
                .contentType("application/json")
                .content(om.writeValueAsString(getTestData().get("karachi"))))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), WeatherDto.class);

        WeatherDto actualRecord = om.readValue(mockMvc.perform(get("/weather/" + expectedRecord.getId())
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), WeatherDto.class);

        assertTrue(new ReflectionEquals(expectedRecord).matches(actualRecord));

        mockMvc.perform(get("/weather/" + Integer.MAX_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private Map<String, WeatherDto> getTestData() throws ParseException {
        Map<String, WeatherDto> data = new LinkedHashMap<>();

        WeatherDto karachi = new WeatherDto(
                simpleDateFormat.parse("2021-09-04"),
                41.8818f,
                -87.6231f,
                "Karachi",
                "Sindh",
                Arrays.asList(24.0, 21.5, 24.0, 19.5, 25.5, 25.5, 24.0, 25.0, 23.0, 22.0, 18.0, 18.0, 23.5, 23.0, 23.0, 25.5, 21.0, 20.5, 20.0, 18.5, 20.5, 21.0, 25.0, 20.5));
        data.put("karachi", karachi);

        WeatherDto lahore = new WeatherDto(
                simpleDateFormat.parse("2021-09-04"),
                37.8043f,
                -122.2711f,
                "Lahore",
                "Punjab",
                Arrays.asList(24.0, 36.0, 28.5, 29.0, 32.0, 36.0, 28.5, 34.5, 30.5, 31.5, 29.5, 27.0, 30.5, 23.5, 29.0, 22.0, 28.5, 32.5, 24.5, 28.5, 22.5, 35.0, 26.5, 32.5));
        data.put("lahore", lahore);

        WeatherDto islamabad = new WeatherDto(
                simpleDateFormat.parse("2021-09-04"),
                51.5098f,
                -0.1180f,
                "Islamabad",
                "N/A",
                Arrays.asList(11.0, 11.0, 5.5, 7.0, 5.0, 5.5, 6.0, 9.5, 11.5, 5.0, 6.0, 8.0, 9.5, 5.0, 9.0, 9.5, 12.0, 6.0, 9.5, 8.5, 8.0, 8.0, 9.0, 6.5));
        data.put("islamabad", islamabad);


        WeatherDto karachi1 = new WeatherDto(
                simpleDateFormat.parse("2019-03-12"),
                55.7512f,
                37.6184f,
                "Karachi",
                "Sindh",
                Arrays.asList(-2.0, -4.5, 1.0, -6.0, 1.0, 1.5, -9.0, -2.5, -3.0, -0.5, -13.5, -9.0, -11.5, -5.5, -5.5, -3.5, -14.0, -9.5, 1.5, -15.0, -6.5, -7.0, -13.5, -14.5));
        data.put("karachi1", karachi1);

        WeatherDto karachi2 = new WeatherDto(
                simpleDateFormat.parse("2020-09-05"),
                55.7512f,
                37.6184f,
                "Karachi",
                "Sindh",
                Arrays.asList(-2.0, -4.5, 1.0, -6.0, 1.0, 1.5, -9.0, -2.5, -3.0, -0.5, -13.5, -9.0, -11.5, -5.5, -5.5, -3.5, -14.0, -9.5, 1.5, -15.0, -6.5, -7.0, -13.5, -14.5));
        data.put("karachi2", karachi2);


        return data;
    }
}

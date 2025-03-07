package com.example.backend.controller;

import com.example.backend.BackendApplication;
import com.example.backend.model.TempModel;
import com.example.backend.model.YearModel;
import com.example.backend.model.YearResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = BackendApplication.class, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class YearRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private static String url;

    @BeforeEach
    public void setup() throws IOException {
        url = "http://localhost:" + port;
    }

    @Test
    public void givenAllYearsAreAvailable_WhenISendGetRequest_ThenIGetAllYearsDetails() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<YearResponse> response = rest.exchange(url+"/api/years",
                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
                });
        assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        YearResponse yearsResponse = response.getBody();
        assertNotNull(yearsResponse);

        List<YearModel> yearModels = yearsResponse.getContent();
        ArrayList<Integer> actualYears = new ArrayList<>();

        for (YearModel model : yearModels) {
            actualYears.add(model.getYear());
        }

        assertEquals(getYears("testdata.json"), actualYears);
    }

    @Test
    public void givenAllYearsAreAvailable_WhenISendPostRequest_ThenNewYearGetsAdded() throws Exception {
        // Create the request body
        YearModel newYear = new YearModel();
        newYear.setYear(2025); // Example year
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBodyJson = objectMapper.writeValueAsString(newYear);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity
        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson, headers);

        // Send POST request
        ResponseEntity<String> response = rest.exchange(url + "/api/years",
                HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});

        // Validate response
        assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).contains("object with id:");
        assertThat(response.getBody()).contains(" saved successfully");

        // Fetch all years and validate the new one was added
        ResponseEntity<YearResponse> getResponse = rest.exchange(url + "/api/years",
                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});

        assertNotNull(getResponse.getBody());
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<YearModel> yearModels = getResponse.getBody().getContent();
        List<Integer> actualYears = new ArrayList<>();
        for (YearModel model : yearModels) {
            actualYears.add(model.getYear());
        }

        assertThat(actualYears).contains(2025);
    }

    private List<Integer> getYears(String filePath) throws Exception {

        ClassPathResource resource = new ClassPathResource(filePath);
        InputStream inputStream = resource.getInputStream();
        List<TempModel> models = new ObjectMapper().readValue(inputStream, new TypeReference<>() {});
        ArrayList<Integer> expectedYears = new ArrayList<>();
        Set<Integer> expectedYearsDuplicatesCheck = new HashSet<>(expectedYears);

        for (TempModel model : models) {
            if (!expectedYearsDuplicatesCheck.contains(model.getYear())) {
                expectedYears.add(model.getYear());
                expectedYearsDuplicatesCheck.add(model.getYear());
            }
        }

        return expectedYears;
    }

}

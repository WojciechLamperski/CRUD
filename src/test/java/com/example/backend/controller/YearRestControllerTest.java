package com.example.backend.controller;

import com.example.backend.BackendApplication;
import com.example.backend.model.TempModel;
import com.example.backend.model.YearModel;
import com.example.backend.model.YearResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.waracle.services.cakemgr.Application;
//import com.waracle.services.cakemgr.model.Cake;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
//    private static List<TempModel> models;

    @BeforeEach
    public void setup() throws IOException {
        url = "http://localhost:" + port;
    }

    @Test
    public void givenAllCakesAreAvailable_WhenISendGetRequest_ThenIGetAllCakesDetails() throws Exception {

        System.out.println(url);

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

        System.out.println("Actual Years: " + actualYears);

        assertEquals(getYears("testdata.json"), actualYears);
    }

    private List<Integer> getYears(String filePath) throws Exception {

        System.out.println("Inside getYears method");

        ClassPathResource resource = new ClassPathResource(filePath);
        InputStream inputStream = resource.getInputStream();

        List<TempModel> models = new ObjectMapper().readValue(inputStream, new TypeReference<>() {});
        System.out.println("Models: " + models);

        // TODO Use a HashMap to get rid of duplicates
        ArrayList<Integer> expectedYears = new ArrayList<>();
        Set<Integer> expectedYearsDuplicatesCheck = new HashSet<>(expectedYears);


        for (TempModel model : models) {
            if (!expectedYearsDuplicatesCheck.contains(model.getYear())) {
                expectedYears.add(model.getYear());
                expectedYearsDuplicatesCheck.add(model.getYear());
            }
        }

        System.out.println("Expected Years: " + expectedYears);

        return expectedYears;
    }

//    @Test
//    public void givenAllCakesAreAvailable_WhenISendPostRequest_ThenNewCakeGetsAdded() throws Exception {
//        String requestBodyJson = new String(Files.readAllBytes(Paths.get(getClass()
//                .getResource("/testdata/request/cake.json").toURI())));
//        Cake expectedCake =  new ObjectMapper().readValue(requestBodyJson, new TypeReference<Cake>() {});
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<String>(requestBodyJson,headers);
//
//        ResponseEntity<String> response = rest.exchange(url+"/cakes",
//                HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
//                });
//
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        entity = new HttpEntity<String>(headers);
//        ResponseEntity<List<Cake>> actualResponse = rest.exchange(url+"/cakes",
//                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Cake>>() {
//                });
//
//        assertNotNull(response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(response.getBody()).contains("Successfully created cake");
//        assertThat(actualResponse.getBody()).contains(expectedCake);
//    }
//
//    private List<Cake> getCakes(String filePath) throws Exception {
//        URL fileUrl = getClass().getResource(filePath);
//        List<Cake> expectedCakes = new ObjectMapper().readValue(fileUrl, new TypeReference<List<Cake>>() {});
//        return expectedCakes;
//
//    }

}

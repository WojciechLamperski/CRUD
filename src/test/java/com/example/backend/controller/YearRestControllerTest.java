package com.example.backend.controller;

import com.example.backend.BackendApplication;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;


import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class YearRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate rest;

    private static String url;

    @BeforeEach
    public void setup() {
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
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        List<Cake> actualCakes = response.getBody();

//        assertEquals(getCakes("testdata.json"), actualCakes);
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

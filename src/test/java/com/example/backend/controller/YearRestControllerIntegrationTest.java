//package com.example.backend.controller;
//
//import com.example.backend.BackendApplication;
//import com.example.backend.entity.YearEntity;
//import com.example.backend.model.TempModel;
//import com.example.backend.model.YearModel;
//import com.example.backend.model.YearResponse;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.*;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
//
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@SpringBootTest(classes = BackendApplication.class, webEnvironment = RANDOM_PORT)
//@ActiveProfiles("test")
//public class YearRestControllerIntegrationTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate rest;
//
//    private static String url;
//
//    @BeforeEach
//    public void setup() throws IOException {
//        url = "http://localhost:" + port;
//    }
//
//    // GET
//    @Test
//    public void givenAllYearsAreAvailable_WhenISendGetRequest_ThenIGetAllYearsDetails() throws Exception {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<YearResponse> response = rest.exchange(url+"/api/years",
//                HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
//                });
//        assertNotNull(response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        YearResponse yearsResponse = response.getBody();
//        assertNotNull(yearsResponse);
//
//        List<YearModel> yearModels = yearsResponse.getContent();
//        ArrayList<Integer> actualYears = new ArrayList<>();
//
//        for (YearModel model : yearModels) {
//            actualYears.add(model.getYear());
//        }
//        assertEquals(getYears("testdata.json"), actualYears);
//    }
//
//    // GET one
//    @Test
//    public void givenYearExists_WhenISendGetRequestForThatYear_ThenIGetYearDetails() throws Exception {
//        // Fetch all years first
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<YearResponse> getResponse = rest.exchange(url + "/api/years",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<YearModel> yearModels = getResponse.getBody().getContent();
//        assertThat(yearModels).isNotEmpty();
//
//        // Pick the first year to fetch individually
//        YearModel expectedYear = yearModels.get(0);
//        int yearId = expectedYear.getYearId();
//
//        // Send GET request for a specific year
//        ResponseEntity<YearModel> response = rest.exchange(url + "/api/years/" + yearId,
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        // Validate response
//        assertNotNull(response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        YearModel actualYear = response.getBody();
//        assertThat(actualYear.getYearId()).isEqualTo(expectedYear.getYearId());
//        assertThat(actualYear.getYear()).isEqualTo(expectedYear.getYear());
//    }
//
//    // POST
//    @Test
//    @DirtiesContext
//    public void givenAllYearsAreAvailable_WhenISendPostRequest_ThenNewYearGetsAdded() throws Exception {
//
//        // Create the request body
//        YearModel newYear = new YearModel();
//        newYear.setYear(2025); // Example year
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBodyJson = objectMapper.writeValueAsString(newYear);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson, headers);
//
//        // Send POST request
//        ResponseEntity<String> response = rest.exchange(url + "/api/years",
//                HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});
//
//        // Validate there is a Body, and correct HTTP status
//        assertNotNull(response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
//        // Validate Body
//        YearEntity yearEntity = new ObjectMapper().readValue(response.getBody(), new TypeReference<>() {});
//        assertThat(yearEntity.getYear()).isEqualTo(2025);
//        assertThat(yearEntity.getYearId()).isEqualTo(getYears("testdata.json").size() + 1);
//
//        // Fetch all years and validate the new one was added
//        ResponseEntity<YearResponse> getResponse = rest.exchange(url + "/api/years",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//        List<YearModel> yearModels = getResponse.getBody().getContent();
//        assertThat(yearModels.get(yearModels.size()-1).getYearId()).isEqualTo(yearEntity.getYearId());
//        assertThat(yearModels.get(yearModels.size()-1).getYear()).isEqualTo(yearEntity.getYear());
//
//    }
//
//    // PUT
//    @Test
//    @DirtiesContext
//    public void givenAllYearsAreAvailable_WhenISendPutRequest_ThenExistingYearGetsUpdated() throws Exception {
//        // Fetch all years first
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<YearResponse> getResponse = rest.exchange(url + "/api/years",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<YearModel> yearModels = getResponse.getBody().getContent();
//        assertThat(yearModels).isNotEmpty();
//
//        // Pick the first year to update
//        YearModel existingYear = yearModels.get(0);
//        int originalId = existingYear.getYearId();
//        int newYearValue = existingYear.getYear() + 1;
//
//        YearModel toBeUpdateYearModel = new YearModel();
//        toBeUpdateYearModel.setYear(newYearValue);
//        toBeUpdateYearModel.setYearId(existingYear.getYearId());
//
//        // Convert to JSON
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBodyJson = objectMapper.writeValueAsString(toBeUpdateYearModel);
//
//        // Send PUT request
//        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson, headers);
//        ResponseEntity<String> putResponse = rest.exchange(url + "/api/years",
//                HttpMethod.PUT, entity, new ParameterizedTypeReference<>() {});
//
//        // Validate response
//        assertNotNull(putResponse.getBody());
//        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        YearEntity yearEntity = new ObjectMapper().readValue(putResponse.getBody(), new TypeReference<>() {});
//        assertThat(yearEntity.getYear()).isEqualTo(newYearValue);
//        assertThat(yearEntity.getYearId()).isEqualTo(originalId);
//
//        // Fetch all years again to verify the update
//        ResponseEntity<YearResponse> verifyResponse = rest.exchange(url + "/api/years",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(verifyResponse.getBody());
//        assertThat(verifyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<YearModel> updatedYearModels = verifyResponse.getBody().getContent();
//        boolean updated = updatedYearModels.stream()
//                .anyMatch(y -> y.getYearId() == originalId && y.getYear() == newYearValue);
//
//        assertThat(updated).isTrue();
//    }
//
//    // DELETE
//    @Test
//    @DirtiesContext
//    public void givenAllYearsAreAvailable_WhenISendDeleteRequest_ThenExistingYearGetsDeleted() throws Exception {
//        // Fetch all years first
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<YearResponse> getResponse = rest.exchange(url + "/api/years",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<YearModel> yearModels = getResponse.getBody().getContent();
//        assertThat(yearModels).isNotEmpty();
//
//        // Pick the first year to delete
//        YearModel yearToDelete = yearModels.get(0);
//        int yearIdToDelete = yearToDelete.getYearId();
//
//        // Send DELETE request
//        ResponseEntity<String> deleteResponse = rest.exchange(url + "/api/years/" + yearIdToDelete,
//                HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        // Validate response
//        assertThat(deleteResponse.getBody()).isNull();
//        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//
//        // Fetch all years to verify deletion
//        ResponseEntity<YearResponse> verifyResponse = rest.exchange(url + "/api/years",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(verifyResponse.getBody());
//        assertThat(verifyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<YearModel> updatedYearModels = verifyResponse.getBody().getContent();
//        boolean exists = updatedYearModels.stream().anyMatch(y -> y.getYearId() == yearIdToDelete);
//
//        // Check that the year no longer exists
//        assertThat(exists).isFalse();
//    }
//
//
//    private List<Integer> getYears(String filePath) throws Exception {
//
//        ClassPathResource resource = new ClassPathResource(filePath);
//        InputStream inputStream = resource.getInputStream();
//        List<TempModel> models = new ObjectMapper().readValue(inputStream, new TypeReference<>() {});
//        ArrayList<Integer> expectedYears = new ArrayList<>();
//        Set<Integer> expectedYearsDuplicatesCheck = new HashSet<>(expectedYears);
//
//        for (TempModel model : models) {
//            if (!expectedYearsDuplicatesCheck.contains(model.getYear())) {
//                expectedYears.add(model.getYear());
//                expectedYearsDuplicatesCheck.add(model.getYear());
//            }
//        }
//
//        return expectedYears;
//    }
//
//    // TODO add error checks
//
//}

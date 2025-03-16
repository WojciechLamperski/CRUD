//package com.example.backend.controller;
//
//import com.example.backend.BackendApplication;
//import com.example.backend.entity.DistrictEntity;
//import com.example.backend.model.*;
//import com.example.backend.model.DistrictModel;
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
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
//
//@SpringBootTest(classes = BackendApplication.class, webEnvironment = RANDOM_PORT)
//@ActiveProfiles("test")
//public class DistrictsRestControllerIntegrationTest {
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
//    // GET all districts
//    @Test
//    public void givenAllDistrictsAreAvailable_WhenISendGetRequest_ThenIGetAllDistrictsDetails() {
//        // Fetch all districts first
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<DistrictResponse> getResponse = rest.exchange(url + "/api/districts",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<DistrictModel> districtModels = getResponse.getBody().getContent();
//        assertThat(districtModels).isNotEmpty();
//
//        // Pick the first district to fetch individually
//        DistrictModel expectedDistrict = districtModels.get(0);
//        int districtId = expectedDistrict.getDistrictId();
//
//        // Send GET request for a specific district
//        ResponseEntity<DistrictModel> response = rest.exchange(url + "/api/districts/" + districtId,
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        // Validate response
//        assertNotNull(response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DistrictModel actualDistrict = response.getBody();
//        assertThat(actualDistrict.getDistrictId()).isEqualTo(expectedDistrict.getDistrictId());
//        assertThat(actualDistrict.getDistrict()).isEqualTo(expectedDistrict.getDistrict());
//    }
//
//    // GET one
//    @Test
//    public void givenDistrictExists_WhenISendGetRequestForThatDistrict_ThenIGetDistrictDetails() {
//        // Fetch all districts first
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<DistrictResponse> getResponse = rest.exchange(url + "/api/districts",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<DistrictModel> districtModels = getResponse.getBody().getContent();
//        assertThat(districtModels).isNotEmpty();
//
//        DistrictModel expectedDistrict = districtModels.get(0);
//        int districtId = expectedDistrict.getDistrictId();
//
//        ResponseEntity<DistrictModel> response = rest.exchange(url + "/api/districts/" + districtId,
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//
//    // POST
//    @Test
//    @Transactional
//    @DirtiesContext
//    public void givenAllDistrictsAreAvailable_WhenISendPostRequest_ThenNewDistrictGetsAdded() throws Exception {
//
//            // Create the request body
//            DistrictEntity newDistrict = new DistrictEntity();
//            newDistrict.setDistrict("zamojski"); // Example year
//            newDistrict.setVoivodeshipId(1);
//            ObjectMapper objectMapper = new ObjectMapper();
//            String requestBodyJson = objectMapper.writeValueAsString(newDistrict);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<String> entity = new HttpEntity<>(requestBodyJson, headers);
//
//            System.out.println(requestBodyJson);
//
//            // Send POST request
//            ResponseEntity<String> response = rest.exchange(url + "/api/years",
//                    HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});
//
//            // Validate there is a Body, and correct HTTP status
//            assertNotNull(response.getBody());
//            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
//            // Validate Body
//            DistrictEntity yearEntity = new ObjectMapper().readValue(response.getBody(), new TypeReference<>() {});
//            assertThat(yearEntity.getDistrict()).isEqualTo(2025);
//            assertThat(yearEntity.getDistrictId()).isEqualTo(getDistricts("testdata.json").size() + 1);
//
//            // Fetch all years and validate the new one was added
//            ResponseEntity<DistrictResponse> getResponse = rest.exchange(url + "/api/years",
//                    HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//            assertNotNull(getResponse.getBody());
//            assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//            List<DistrictModel> districtModels = getResponse.getBody().getContent();
//            assertThat(districtModels.get(districtModels.size()-1).getDistrictId()).isEqualTo(yearEntity.getDistrictId());
//            assertThat(districtModels.get(districtModels.size()-1).getDistrict()).isEqualTo(yearEntity.getDistrict());
//
//    }
//
//    // PUT
//    @Test
//    @Transactional
//    @DirtiesContext
//    public void givenAllDistrictsAreAvailable_WhenISendPutRequest_ThenExistingDistrictGetsUpdated() throws Exception {
//        // Fetch all districts first
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<DistrictResponse> getResponse = rest.exchange(url + "/api/districts",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<DistrictModel> districtModels = getResponse.getBody().getContent();
//        assertThat(districtModels).isNotEmpty();
//
//        DistrictModel existingDistrict = districtModels.get(0);
//        int originalId = existingDistrict.getDistrictId();
//        String newDistrict = "wrocławski";
//
//        DistrictModel toBeUpdatedDistrictModel = new DistrictModel();
//        toBeUpdatedDistrictModel.setDistrict(newDistrict);
//        districtModels.add(0, toBeUpdatedDistrictModel);
//
//        // Convert to JSON
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBodyJson = objectMapper.writeValueAsString(districtModels);
//
//        // Send PUT request
//        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson, headers);
//        ResponseEntity<String> putResponse = rest.exchange(url + "/api/districts",
//                HttpMethod.PUT, entity, new ParameterizedTypeReference<>() {});
//
//        // Validate response
//        assertNotNull(putResponse.getBody());
//        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        DistrictEntity districtEntity = new ObjectMapper().readValue(putResponse.getBody(), new TypeReference<>() {});
//        assertThat(districtEntity.getDistrict()).isEqualTo(newDistrict);
//        assertThat(districtEntity.getDistrictId()).isEqualTo(originalId);
//
//        // Fetch all districts again to verify the update
//        ResponseEntity<DistrictResponse> verifyResponse = rest.exchange(url + "/api/districts",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<DistrictModel> updatedDistrictModels = verifyResponse.getBody().getContent();
//        boolean updated = updatedDistrictModels.stream()
//                .anyMatch(d -> d.getDistrictId() == originalId && d.getDistrict() == newDistrict);
//
//        assertThat(updated).isTrue();
//    }
//
//    // DELETE
//    @Test
//    @DirtiesContext
//    public void givenAllDistrictsAreAvailable_WhenISendDeleteRequest_ThenExistingDistrictGetsDeleted() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<DistrictResponse> getResponse = rest.exchange(url + "/api/districts",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<DistrictModel> districtModels = getResponse.getBody().getContent();
//        assertThat(districtModels).isNotEmpty();
//
//        DistrictModel districtToDelete = districtModels.get(0);
//        int districtId = districtToDelete.getDistrictId();
//
//        ResponseEntity<String> deleteResponse = rest.exchange(url + "/api/districts/" + districtId,
//                HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }
//
//    private List<String> getDistricts(String filePath) throws Exception {
//
//        ClassPathResource resource = new ClassPathResource(filePath);
//        InputStream inputStream = resource.getInputStream();
//        List<TempModel> models = new ObjectMapper().readValue(inputStream, new TypeReference<>() {});
//        ArrayList<String> expectedDistricts = new ArrayList<>();
//        Set<String> expectedDistrictsDuplicatesCheck = new HashSet<>(expectedDistricts);
//
//        for (TempModel model : models) {
//            if (!expectedDistrictsDuplicatesCheck.contains(model.getDistrict())) {
//                expectedDistricts.add(model.getDistrict());
//                expectedDistrictsDuplicatesCheck.add(model.getDistrict());
//            }
//        }
//
//        return expectedDistricts;
//    }
//
//}

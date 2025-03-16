//package com.example.backend.controller;
//
//import com.example.backend.BackendApplication;
//import com.example.backend.entity.VoivodeshipEntity;
//import com.example.backend.model.*;
//import com.example.backend.model.VoivodeshipModel;
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
//public class VoivodeshipsRestControllerIntegrationTest {
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
//    // GET all voivodeships
//    @Test
//    public void givenAllVoivodeshipsAreAvailable_WhenISendGetRequest_ThenIGetAllVoivodeshipsDetails() {
//        // Fetch all voivodeships first
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<VoivodeshipResponse> getResponse = rest.exchange(url + "/api/voivodeships",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<VoivodeshipModel> voivodeshipModels = getResponse.getBody().getContent();
//        assertThat(voivodeshipModels).isNotEmpty();
//
//        // Pick the first voivodeship to fetch individually
//        VoivodeshipModel expectedVoivodeship = voivodeshipModels.get(0);
//        int voivodeshipId = expectedVoivodeship.getVoivodeshipId();
//
//        // Send GET request for a specific voivodeship
//        ResponseEntity<VoivodeshipModel> response = rest.exchange(url + "/api/voivodeships/" + voivodeshipId,
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        // Validate response
//        assertNotNull(response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        VoivodeshipModel actualVoivodeship = response.getBody();
//        assertThat(actualVoivodeship.getVoivodeshipId()).isEqualTo(expectedVoivodeship.getVoivodeshipId());
//        assertThat(actualVoivodeship.getVoivodeship()).isEqualTo(expectedVoivodeship.getVoivodeship());
//    }
//
//    // GET one
//    @Test
//    public void givenVoivodeshipExists_WhenISendGetRequestForThatVoivodeship_ThenIGetVoivodeshipDetails() {
//        // Fetch all voivodeships first
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<VoivodeshipResponse> getResponse = rest.exchange(url + "/api/voivodeships",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<VoivodeshipModel> voivodeshipModels = getResponse.getBody().getContent();
//        assertThat(voivodeshipModels).isNotEmpty();
//
//        VoivodeshipModel expectedVoivodeship = voivodeshipModels.get(0);
//        int voivodeshipId = expectedVoivodeship.getVoivodeshipId();
//
//        ResponseEntity<VoivodeshipModel> response = rest.exchange(url + "/api/voivodeships/" + voivodeshipId,
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(response.getBody());
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }
//
//    // POST
//    @Test
//    @DirtiesContext
//    public void givenAllVoivodeshipsAreAvailable_WhenISendPostRequest_ThenNewVoivodeshipGetsAdded() throws Exception {
//        // Create the request body
//        VoivodeshipModel newVoivodeship = new VoivodeshipModel();
//        newVoivodeship.setVoivodeship("New Voivodeship"); // Example name
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBodyJson = objectMapper.writeValueAsString(newVoivodeship);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson, headers);
//
//        // Send POST request
//        ResponseEntity<String> postResponse = rest.exchange(url + "/api/voivodeships",
//                HttpMethod.POST, entity, new ParameterizedTypeReference<>() {});
//
//        // Validate the response
//        assertNotNull(postResponse.getBody());
//        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//
//        // Validate Body
//        VoivodeshipEntity createdVoivodeship = objectMapper.readValue(postResponse.getBody(), new TypeReference<>() {});
//        assertThat(createdVoivodeship.getVoivodeship()).isEqualTo(newVoivodeship.getVoivodeship());
//
//        // Fetch all voivodeships and validate the new one was added
//        ResponseEntity<VoivodeshipResponse> getResponse = rest.exchange(url + "/api/voivodeships",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<VoivodeshipModel> voivodeshipModels = getResponse.getBody().getContent();
//        assertThat(voivodeshipModels).isNotEmpty();
//
//        // Check if the new Voivodeship is present
//        VoivodeshipModel lastVoivodeship = voivodeshipModels.get(voivodeshipModels.size() - 1);
//        assertThat(lastVoivodeship.getVoivodeship()).isEqualTo(createdVoivodeship.getVoivodeship());
//    }
//
//
//    // PUT
//    @Test
//    @DirtiesContext
//    public void givenAllVoivodeshipsAreAvailable_WhenISendPutRequest_ThenExistingVoivodeshipGetsUpdated() throws Exception {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<VoivodeshipResponse> getResponse = rest.exchange(url + "/api/voivodeships",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<VoivodeshipModel> voivodeshipModels = getResponse.getBody().getContent();
//        assertThat(voivodeshipModels).isNotEmpty();
//
//        VoivodeshipModel existingVoivodeship = voivodeshipModels.get(0);
//        int originalId = existingVoivodeship.getVoivodeshipId();
//        String newVoivodeshipName = existingVoivodeship.getVoivodeship() + " Updated";
//
//        VoivodeshipModel toBeUpdatedVoivodeship = new VoivodeshipModel();
//        toBeUpdatedVoivodeship.setVoivodeshipId(originalId);
//        toBeUpdatedVoivodeship.setVoivodeship(newVoivodeshipName);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String requestBodyJson = objectMapper.writeValueAsString(toBeUpdatedVoivodeship);
//
//        HttpEntity<String> entity = new HttpEntity<>(requestBodyJson, headers);
//        ResponseEntity<String> putResponse = rest.exchange(url + "/api/voivodeships",
//                HttpMethod.PUT, entity, new ParameterizedTypeReference<>() {});
//
//        assertNotNull(putResponse.getBody());
//        assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        VoivodeshipEntity updatedVoivodeship = objectMapper.readValue(putResponse.getBody(), new TypeReference<>() {});
//        assertThat(updatedVoivodeship.getVoivodeshipId()).isEqualTo(originalId);
//        assertThat(updatedVoivodeship.getVoivodeship()).isEqualTo(newVoivodeshipName);
//    }
//
//    // DELETE
//    @Test
//    @DirtiesContext
//    public void givenAllVoivodeshipsAreAvailable_WhenISendDeleteRequest_ThenExistingVoivodeshipGetsDeleted() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        ResponseEntity<VoivodeshipResponse> getResponse = rest.exchange(url + "/api/voivodeships",
//                HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertNotNull(getResponse.getBody());
//        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<VoivodeshipModel> voivodeshipModels = getResponse.getBody().getContent();
//        assertThat(voivodeshipModels).isNotEmpty();
//
//        VoivodeshipModel voivodeshipToDelete = voivodeshipModels.get(0);
//        int voivodeshipId = voivodeshipToDelete.getVoivodeshipId();
//
//        ResponseEntity<String> deleteResponse = rest.exchange(url + "/api/voivodeships/" + voivodeshipId,
//                HttpMethod.DELETE, new HttpEntity<>(headers), new ParameterizedTypeReference<>() {});
//
//        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }
//
//    private List<String> getVoivodeships(String filePath) throws Exception {
//
//        ClassPathResource resource = new ClassPathResource(filePath);
//        InputStream inputStream = resource.getInputStream();
//        List<TempModel> models = new ObjectMapper().readValue(inputStream, new TypeReference<>() {});
//        ArrayList<String> expectedVoivodeships = new ArrayList<>();
//        Set<String> expectedVoivodeshipsDuplicatesCheck = new HashSet<>(expectedVoivodeships);
//
//        for (TempModel model : models) {
//            if (!expectedVoivodeshipsDuplicatesCheck.contains(model.getVoivodeship())) {
//                expectedVoivodeships.add(model.getVoivodeship());
//                expectedVoivodeshipsDuplicatesCheck.add(model.getVoivodeship());
//            }
//        }
//
//        return expectedVoivodeships;
//    }
//
//}

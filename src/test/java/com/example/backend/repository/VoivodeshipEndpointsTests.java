package com.example.backend.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.backend.POJO.Voivodeship;
import java.util.List;
import com.example.backend.DTO.VoivodeshipResponse;
import org.springframework.transaction.annotation.Transactional;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class VoivodeshipEndpointsTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllVoivodeshipsIsPopulated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].voivodeshipId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].voivodeship").isString());
    }

    @Test
    public void testGetVoivodeshipById() throws Exception {

        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString(); // Check if 'id' in the response matches testId

        // Return the content (the list of voivodeships)
        List<Voivodeship> content = objectMapper.readValue(responseJson, VoivodeshipResponse.class).getContent();

        int testId = content.get(0).getVoivodeshipId(); // The ID you want to test

        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships/{id}", testId))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.voivodeshipId").value(testId)); // Check if 'id' in the response matches testId
    }

    // Create Voivodeship
    @Test
    @Transactional
    public void testSaveVoivodeship() throws Exception {
        // Create a new Voivodeship object to be created
        Voivodeship newVoivodeship = new Voivodeship();
        newVoivodeship.setVoivodeship("WYMYŚLONE");

        // Convert the Voivodeship object to JSON
        String newVoivodeshipJson = objectMapper.writeValueAsString(newVoivodeship);

        // Perform a POST request to create the new Voivodeship
        String responseMessage = mockMvc.perform(MockMvcRequestBuilders.post("/api/voivodeships")
                        .contentType("application/json")
                        .content(newVoivodeshipJson))
                .andExpect(MockMvcResultMatchers.status().isCreated()) // Expect status 201 (Created)
                .andReturn()
                .getResponse()
                .getContentAsString(); // Get response as a string

        // Verify that the response contains the expected message
        assert responseMessage.contains("object with id:");
        assert responseMessage.contains("saved successfully");
    }

    @Test
    @Transactional
    public void testUpdateVoivodeship() throws Exception {

        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString(); // Check if 'id' in the response matches testId

        // Return the content (the list of voivodeships)
        List<Voivodeship> content = objectMapper.readValue(responseJson, VoivodeshipResponse.class).getContent();

        int testId = content.get(0).getVoivodeshipId();

        // Create an updated Voivodeship object
        Voivodeship updatedVoivodeship = new Voivodeship();
        updatedVoivodeship.setVoivodeshipId(testId);
        updatedVoivodeship.setVoivodeship("WYMYŚLONE2");

        // Convert the updated Voivodeship object to JSON
        String updatedVoivodeshipJson = objectMapper.writeValueAsString(updatedVoivodeship);

        // Perform a PUT request to update the existing Voivodeship
        String responseMessage = mockMvc.perform(MockMvcRequestBuilders.put("/api/voivodeships")
                        .contentType("application/json")
                        .content(updatedVoivodeshipJson))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect status 200 (OK)
                .andReturn()
                .getResponse()
                .getContentAsString(); // Get response as a string

        // Verify that the response contains the expected message
        assert responseMessage.contains("object with id:");
        assert responseMessage.contains("saved successfully");
    }

    @Test
    @Transactional
    public void testDeleteVoivodeship() throws Exception {

        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString(); // Check if 'id' in the response matches testId

        // Return the content (the list of voivodeships)
        List<Voivodeship> content = objectMapper.readValue(responseJson, VoivodeshipResponse.class).getContent();

        int testId = content.get(0).getVoivodeshipId();

        // Perform a DELETE request to delete the voivodeship with the given ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/voivodeships/{id}", testId))
                .andExpect(MockMvcResultMatchers.content().string("Voivodeship successfully deleted"));
    }

    // Test sortOrder
    // Test sortBy Voivodeship
    @Test
    public void testSortingOrderAndSortByField() throws Exception {
        // Test sorting by "voivodeshipId" ascending
        String ascResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships")
                        .param("sortBy", "voivodeship")
                        .param("sortDirection", "asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Voivodeship> ascList = objectMapper.readValue(ascResponse, VoivodeshipResponse.class).getContent();

        // Test sorting by "voivodeshipId" descending
        String descResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships")
                        .param("sortBy", "voivodeship")
                        .param("sortDirection", "desc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Voivodeship> descList = objectMapper.readValue(descResponse, VoivodeshipResponse.class).getContent();

        assert !ascList.isEmpty();
        assert !descList.isEmpty();
        assert ascList.size() == descList.size();

        if(ascList.size() > 1) {
            assert(ascList.get(0) != descList.get(0));
        }
    }

    // Test Page
    @Test
    public void testPageChange() throws Exception {
        // Test sorting by "voivodeshipId" ascending
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships")
                        .param("pageNumber", "1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(1));
    }

    // Test PageSize
    @Test
    public void testPageSize() throws Exception {
        // Test sorting by "voivodeshipId" ascending
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").isNumber());
    }


    // Test Exception Handling
    @Test
    public void testHandleTypeMismatchException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships/{voivodeshipId}", "invalidVoivodeshipId")) // passing a string instead of a valid integer
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // Expect 400 Bad Request
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid parameter type: voivodeshipId. Expected type: int"));
    }

    @Test
    public void testVoivodeshipNotFoundException() throws Exception {
        int nonExistentVoivodeshipId = 99999; // Assume this `voivodeshipId` does not exist
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships/{voivodeshipId}", nonExistentVoivodeshipId))
                .andExpect(MockMvcResultMatchers.status().isNotFound()) // Expect 404 Not Found
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Voivodeship not found"));
    }

    @Test
    public void testHandleHttpMessageNotReadableException() throws Exception {
        String invalidJson = "{ invalid json "; // malformed JSON

        mockMvc.perform(MockMvcRequestBuilders.post("/api/voivodeships")
                        .contentType("application/json")
                        .content(invalidJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // Expect 400 Bad Request
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid input. Please provide a JSON object with the required fields."));
    }

    @Test
    public void testValidationException() throws Exception {
        // Assuming that the `Voivodeship` entity has validation annotations like @NotNull or @Min
        Voivodeship invalidVoivodeship = new Voivodeship(); // Missing required fields
        String invalidVoivodeshipJson = objectMapper.writeValueAsString(invalidVoivodeship);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/voivodeships")
                        .contentType("application/json")
                        .content(invalidVoivodeshipJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // Expect 400 Bad Request
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("voivodeship can't be empty"));
    }

    @Test
    public void testInvalidSortByField() throws Exception {
        // Simulate an invalid sortBy field
        String invalidSortBy = "nonExistingField";

        // Perform the GET request with the invalid sortBy
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships")
                        .param("sortBy", invalidSortBy)
                        .param("pageNumber", "0")
                        .param("pageSize", "20")
                        .param("sortDirection", "asc"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // Expect 400 Bad Request
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid sort field: " + invalidSortBy + ". Allowed fields: [voivodeshipId, voivodeship]"));
    }

}

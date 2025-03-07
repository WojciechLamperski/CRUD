package com.example.backend.TEMP_endpoints;

import com.example.backend.model.VoivodeshipModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.backend.entity.VoivodeshipEntity;
import java.util.List;
import com.example.backend.model.VoivodeshipResponse;
import org.springframework.transaction.annotation.Transactional;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class VoivodeshipEndpointsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllVoivodeshipsIsPopulated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].voivodeshipId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].voivodeship").isString());
    }

    @Test
    public void testGetVoivodeshipById() throws Exception {

        int testId = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships/{id}", testId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                // Check if 'id' in the response matches testId
                .andExpect(MockMvcResultMatchers.jsonPath("$.voivodeshipId").value(testId));
    }

    @Test
    @Transactional
    public void testSaveVoivodeship() throws Exception {
        // Create a new Voivodeship object to be created
        VoivodeshipEntity newVoivodeship = new VoivodeshipEntity();
        newVoivodeship.setVoivodeship("WYMYŚLONE");

        // Convert the Voivodeship object to JSON
        String newVoivodeshipJson = objectMapper.writeValueAsString(newVoivodeship);

        // Perform a POST request to create the new Voivodeship
        String responseMessage = mockMvc.perform(MockMvcRequestBuilders.post("/api/voivodeships")
                        .contentType("application/json")
                        .content(newVoivodeshipJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Verify that the response contains the expected message
        assert responseMessage.contains("object with id:");
        assert responseMessage.contains("saved successfully");
    }

    @Test
    @Transactional
    public void testUpdateVoivodeship() throws Exception {

        int testId = 1;

        // Create an updated Voivodeship object
        VoivodeshipEntity updatedVoivodeship = new VoivodeshipEntity();
        updatedVoivodeship.setVoivodeshipId(testId);
        updatedVoivodeship.setVoivodeship("WYMYŚLONE2");

        // Convert the updated Voivodeship object to JSON
        String updatedVoivodeshipJson = objectMapper.writeValueAsString(updatedVoivodeship);

        // Perform a PUT request to update the existing Voivodeship
        String responseMessage = mockMvc.perform(MockMvcRequestBuilders.put("/api/voivodeships")
                        .contentType("application/json")
                        .content(updatedVoivodeshipJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Verify that the response contains the expected message
        assert responseMessage.contains("object with id:");
        assert responseMessage.contains("saved successfully");
    }

    @Test
    @Transactional
    public void testDeleteVoivodeship() throws Exception {

        int testId = 1;

        // Perform a DELETE request to delete the voivodeship with the given ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/voivodeships/{id}", testId))
                .andExpect(MockMvcResultMatchers.content().string("Voivodeship successfully deleted"));
    }

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

        List<VoivodeshipModel> ascList = objectMapper.readValue(ascResponse, VoivodeshipResponse.class).getContent();

        // Test sorting by "voivodeshipId" descending
        String descResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships")
                        .param("sortBy", "voivodeship")
                        .param("sortDirection", "desc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<VoivodeshipModel> descList = objectMapper.readValue(descResponse, VoivodeshipResponse.class).getContent();

        assert !ascList.isEmpty();
        assert !descList.isEmpty();
        assert ascList.size() == descList.size();

        if(ascList.size() > 1) {
            assert(ascList.getFirst() != descList.getFirst());
        }
    }

    @Test
    public void testPageChange() throws Exception {
        // Test sorting by "voivodeshipId" ascending
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships")
                        .param("pageNumber", "1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(1));
    }

    @Test
    public void testPageSize() throws Exception {
        // Test sorting by "voivodeshipId" ascending
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").isNumber());
    }

    // Test Exception Handling
    @Test
    public void testHandleTypeMismatchException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships/{voivodeshipId}", "invalidVoivodeshipId")) // passing a string instead of a valid integer
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid parameter type: voivodeshipId. Expected type: int"));
    }

    @Test
    public void testVoivodeshipNotFoundException() throws Exception {

        // Using `voivodeshipId` that doesn't exist
        int nonExistentVoivodeshipId = 99999;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships/{voivodeshipId}", nonExistentVoivodeshipId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Voivodeship not found"));
    }

    @Test
    public void testHandleHttpMessageNotReadableException() throws Exception {
        // Using malformed JSON
        String invalidJson = "{ invalid json ";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/voivodeships")
                        .contentType("application/json")
                        .content(invalidJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid input. Please provide a JSON object with the required fields."));
    }

    @Test
    public void testValidationException() throws Exception {
        // Assuming that the `Voivodeship` entity has validation annotations like @NotNull or @Min
        VoivodeshipEntity invalidVoivodeship = new VoivodeshipEntity(); // Missing required fields
        String invalidVoivodeshipJson = objectMapper.writeValueAsString(invalidVoivodeship);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/voivodeships")
                        .contentType("application/json")
                        .content(invalidVoivodeshipJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
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
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid sort field: " + invalidSortBy + ". Allowed fields: [voivodeshipId, voivodeship]"));
    }

}

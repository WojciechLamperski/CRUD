package com.example.backend.endpoints;

import com.example.backend.DTO.PopulationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.backend.POJO.Population;
import java.util.List;
import com.example.backend.DTO.PopulationResponse;
import org.springframework.transaction.annotation.Transactional;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class PopulationEndpointsTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllPopulationsIsPopulated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/populations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].populationId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].men").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].women").isNumber());
    }

    @Test
    public void testGetAllPopulationsInVoivodeshipIsPopulated() throws Exception {

        int testId = 2;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships/{voivodeshipId}/populations", testId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].populationId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].men").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].women").isNumber());
    }

    @Test
    public void testGetAllPopulationsInDistrictIsPopulated() throws Exception {

        int testId = 2;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/districts/{districtId}/populations", testId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].populationId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].men").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].women").isNumber());
    }

    @Test
    public void testGetAllPopulationsInYearIsPopulated() throws Exception {

        int testId = 2;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/years/{yearId}/populations", testId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].populationId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].men").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].women").isNumber());
    }

    @Test
    public void testGetPopulationById() throws Exception {

        int testId = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/populations/{id}", testId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                // Check if 'id' in the response matches testId
                .andExpect(MockMvcResultMatchers.jsonPath("$.populationId").value(testId));
    }

    @Test
    @Transactional
    public void testSavePopulation() throws Exception {
        // Create a new Population object to be created
        Population newPopulation = new Population();
        newPopulation.setDistrictId(1);
        newPopulation.setYearId(1);
        newPopulation.setMen(1234);
        newPopulation.setWomen(4321);

        // Convert the Population object to JSON
        String newPopulationJson = objectMapper.writeValueAsString(newPopulation);

        // Perform a POST request to create the new Population
        String responseMessage = mockMvc.perform(MockMvcRequestBuilders.post("/api/populations")
                        .contentType("application/json")
                        .content(newPopulationJson))
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
    public void testUpdatePopulation() throws Exception {

        int testId = 1;

        // Create an updated Population object
        Population updatedPopulation = new Population();
        updatedPopulation.setPopulationId(testId);
        updatedPopulation.setWomen(1234);
        updatedPopulation.setMen(4321);

        // Convert the updated Population object to JSON
        String updatedPopulationJson = objectMapper.writeValueAsString(updatedPopulation);

        // Perform a PUT request to update the existing Population
        String responseMessage = mockMvc.perform(MockMvcRequestBuilders.put("/api/populations")
                        .contentType("application/json")
                        .content(updatedPopulationJson))
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
    public void testDeletePopulation() throws Exception {

        int testId = 1;

        // Perform a DELETE request to delete the population with the given ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/populations/{id}", testId))
                .andExpect(MockMvcResultMatchers.content().string("Population successfully deleted"));
    }

    @Test
    public void testSortingOrderAndSortByField() throws Exception {
        // Test sorting by "populationId" ascending
        String ascResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/populations")
                        .param("sortBy", "populationId")
                        .param("sortDirection", "asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PopulationDTO> ascList = objectMapper.readValue(ascResponse, PopulationResponse.class).getContent();

        // Test sorting by "populationId" descending
        String descResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/populations")
                        .param("sortBy", "populationId")
                        .param("sortDirection", "desc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PopulationDTO> descList = objectMapper.readValue(descResponse, PopulationResponse.class).getContent();

        assert !ascList.isEmpty();
        assert !descList.isEmpty();
        assert ascList.size() == descList.size();

        if(ascList.size() > 1) {
            assert(ascList.getFirst() != descList.getFirst());
        }
    }

    @Test
    public void testPageChange() throws Exception {
        // Test sorting by "populationId" ascending
        mockMvc.perform(MockMvcRequestBuilders.get("/api/populations")
                        .param("pageNumber", "1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(1));
    }

    @Test
    public void testPageSize() throws Exception {
        // Test sorting by "populationId" ascending
        mockMvc.perform(MockMvcRequestBuilders.get("/api/populations"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").isNumber());
    }


    // Test Exception Handling
    @Test
    public void testHandleTypeMismatchException() throws Exception {

        // passing a string instead of a valid integer
        mockMvc.perform(MockMvcRequestBuilders.get("/api/populations/{populationId}", "invalidPopulationId"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid parameter type: populationId. Expected type: int"));
    }

    @Test
    public void testPopulationNotFoundException() throws Exception {
        int nonExistentPopulationId = 99999; // Assume this `populationId` does not exist
        mockMvc.perform(MockMvcRequestBuilders.get("/api/populations/{populationId}", nonExistentPopulationId))
                .andExpect(MockMvcResultMatchers.status().isNotFound()) // Expect 404 Not Found
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Population not found"));
    }

    @Test
    public void testHandleHttpMessageNotReadableException() throws Exception {
        String invalidJson = "{ invalid json "; // malformed JSON

        mockMvc.perform(MockMvcRequestBuilders.post("/api/populations")
                        .contentType("application/json")
                        .content(invalidJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid input. Please provide a JSON object with the required fields."));
    }

    @Test
    public void testValidationException() throws Exception {
        // Assuming that the `Population` entity has validation annotations like @NotNull or @Min
        Population invalidPopulation = new Population(); // Missing required fields
        String invalidPopulationJson = objectMapper.writeValueAsString(invalidPopulation);

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/populations")
                        .contentType("application/json")
                        .content(invalidPopulationJson))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        // Verify that the response contains the expected message
        assert response.contains("can't be null");
    }

    @Test
    public void testInvalidSortByField() throws Exception {
        // Simulate an invalid sortBy field
        String invalidSortBy = "nonExistingField";

        // Perform the GET request with the invalid sortBy
        mockMvc.perform(MockMvcRequestBuilders.get("/api/populations")
                        .param("sortBy", invalidSortBy)
                        .param("pageNumber", "0")
                        .param("pageSize", "20")
                        .param("sortDirection", "asc"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid sort field: " + invalidSortBy + ". Allowed fields: [populationId, yearId, districtId, men, women]"));
    }

}

package com.example.backend.endpoints;

import com.example.backend.DTO.DistrictDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.backend.POJO.District;
import java.util.List;
import com.example.backend.DTO.DistrictResponse;
import org.springframework.transaction.annotation.Transactional;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class DistrictEndpointsTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllDistrictsIsPopulated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/districts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].districtId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].district").isString());
    }

    @Test
    public void testGetAllDistrictsInVoivodeshipIsPopulated() throws Exception {

        int testId = 2;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/voivodeships/{voivodeshipId}/districts", testId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].districtId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].voivodeship").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].district").isString());
    }

    @Test
    public void testGetDistrictById() throws Exception {

        int testId = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/districts/{id}", testId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.districtId").value(testId)); // Check if 'id' in the response matches testId
    }

    @Test
    @Transactional
    public void testSaveDistrict() throws Exception {
        // Create a new District object to be created
        District newDistrict = new District();
        newDistrict.setDistrict("wymyślony");

        // Convert the District object to JSON
        String newDistrictJson = objectMapper.writeValueAsString(newDistrict);

        // Perform a POST request to create the new District
        String responseMessage = mockMvc.perform(MockMvcRequestBuilders.post("/api/districts")
                        .contentType("application/json")
                        .content(newDistrictJson))
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
    public void testUpdateDistrict() throws Exception {

        int testId = 1;

        // Create an updated District object
        District updatedDistrict = new District();
        updatedDistrict.setDistrictId(testId);
        updatedDistrict.setDistrict("wymyślony2");

        // Convert the updated District object to JSON
        String updatedDistrictJson = objectMapper.writeValueAsString(updatedDistrict);

        // Perform a PUT request to update the existing District
        String responseMessage = mockMvc.perform(MockMvcRequestBuilders.put("/api/districts")
                        .contentType("application/json")
                        .content(updatedDistrictJson))
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
    public void testDeleteDistrict() throws Exception {

        int testId = 1;

        // Perform a DELETE request to delete the district with the given ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/districts/{id}", testId))
                .andExpect(MockMvcResultMatchers.content().string("District successfully deleted"));
    }

    @Test
    public void testSortingOrderAndSortByField() throws Exception {
        // Test sorting by "districtId" ascending
        String ascResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/districts")
                        .param("sortBy", "district")
                        .param("sortDirection", "asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<DistrictDTO> ascList = objectMapper.readValue(ascResponse, DistrictResponse.class).getContent();

        // Test sorting by "districtId" descending
        String descResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/districts")
                        .param("sortBy", "district")
                        .param("sortDirection", "desc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<DistrictDTO> descList = objectMapper.readValue(descResponse, DistrictResponse.class).getContent();

        assert !ascList.isEmpty();
        assert !descList.isEmpty();
        assert ascList.size() == descList.size();

        if(ascList.size() > 1) {
            assert(ascList.get(0) != descList.get(0));
        }
    }

    @Test
    public void testPageChange() throws Exception {
        // Test sorting by "districtId" ascending
        mockMvc.perform(MockMvcRequestBuilders.get("/api/districts")
                        .param("pageNumber", "1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(1));
    }

    @Test
    public void testPageSize() throws Exception {
        // Test sorting by "districtId" ascending
        mockMvc.perform(MockMvcRequestBuilders.get("/api/districts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").isNumber());
    }


    // Test Exception Handling
    @Test
    public void testHandleTypeMismatchException() throws Exception {
        // passing a string instead of a valid integer
        mockMvc.perform(MockMvcRequestBuilders.get("/api/districts/{districtId}", "invalidDistrictId"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid parameter type: districtId. Expected type: int"));
    }

    @Test
    public void testDistrictNotFoundException() throws Exception {
        int nonExistentDistrictId = 99999; // Assume this `districtId` does not exist
        mockMvc.perform(MockMvcRequestBuilders.get("/api/districts/{districtId}", nonExistentDistrictId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("District not found"));
    }

    @Test
    public void testHandleHttpMessageNotReadableException() throws Exception {
        String invalidJson = "{ invalid json "; // malformed JSON

        mockMvc.perform(MockMvcRequestBuilders.post("/api/districts")
                        .contentType("application/json")
                        .content(invalidJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid input. Please provide a JSON object with the required fields."));
    }

    @Test
    public void testValidationException() throws Exception {
        // Assuming that the `District` entity has validation annotations like @NotNull or @Min
        District invalidDistrict = new District(); // Missing required fields
        String invalidDistrictJson = objectMapper.writeValueAsString(invalidDistrict);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/districts")
                        .contentType("application/json")
                        .content(invalidDistrictJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("district can't be empty"));
    }

    @Test
    public void testInvalidSortByField() throws Exception {
        // Simulate an invalid sortBy field
        String invalidSortBy = "nonExistingField";

        // Perform the GET request with the invalid sortBy
        mockMvc.perform(MockMvcRequestBuilders.get("/api/districts")
                        .param("sortBy", invalidSortBy)
                        .param("pageNumber", "0")
                        .param("pageSize", "20")
                        .param("sortDirection", "asc"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid sort field: " + invalidSortBy + ". Allowed fields: [districtId, district]"));
    }

}

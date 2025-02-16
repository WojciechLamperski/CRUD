package com.example.backend.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.example.backend.POJO.Year;
import java.util.List;
import com.example.backend.DTO.YearResponse;
import org.springframework.transaction.annotation.Transactional;


@AutoConfigureMockMvc
@SpringBootTest
public class YearDAOImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllYearsIsPopulated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/years"))
            .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
            .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].yearId").isNumber())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].year").isNumber());
    }

    @Test
    public void testGetYearById() throws Exception {

        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/years"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString(); // Check if 'id' in the response matches testId

        // Return the content (the list of years)
        List<Year> content = objectMapper.readValue(responseJson, YearResponse.class).getContent();

        int testId = content.get(0).getYearId(); // The ID you want to test

        mockMvc.perform(MockMvcRequestBuilders.get("/api/years/{id}", testId))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.yearId").value(testId)); // Check if 'id' in the response matches testId
    }

    // Create Year
    @Test
    @Transactional
    public void testSaveYear() throws Exception {
        // Create a new Year object to be created
        Year newYear = new Year();
        newYear.setYear(9999);

        // Convert the Year object to JSON
        String newYearJson = objectMapper.writeValueAsString(newYear);

        // Perform a POST request to create the new Year
        String responseMessage = mockMvc.perform(MockMvcRequestBuilders.post("/api/years")
                        .contentType("application/json")
                        .content(newYearJson))
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
    public void testUpdateYear() throws Exception {

        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/years"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString(); // Check if 'id' in the response matches testId

        // Return the content (the list of years)
        List<Year> content = objectMapper.readValue(responseJson, YearResponse.class).getContent();

        int testId = content.get(0).getYearId();

        // Create an updated Year object
        Year updatedYear = new Year();
        updatedYear.setYearId(testId);
        updatedYear.setYear(6666);

        // Convert the updated Year object to JSON
        String updatedYearJson = objectMapper.writeValueAsString(updatedYear);

        // Perform a PUT request to update the existing Year
        String responseMessage = mockMvc.perform(MockMvcRequestBuilders.put("/api/years")
                        .contentType("application/json")
                        .content(updatedYearJson))
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
    public void testDeleteYear() throws Exception {

        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/years"))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Check HTTP status is 200
                .andExpect(MockMvcResultMatchers.content().contentType("application/json")) // Check if content type is JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString(); // Check if 'id' in the response matches testId

        // Return the content (the list of years)
        List<Year> content = objectMapper.readValue(responseJson, YearResponse.class).getContent();

        int testId = content.get(0).getYearId();

        // Perform a DELETE request to delete the year with the given ID
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/years/{id}", testId))
                .andExpect(MockMvcResultMatchers.content().string("Year successfully deleted"));
    }

    // Test sortOrder
    @Test
    public void testSortingOrder() throws Exception {
        // Test sorting by "yearId" ascending
        String ascResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/years")
                        .param("sortDirection", "asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Year> ascList = objectMapper.readValue(ascResponse, YearResponse.class).getContent();

        // Test sorting by "yearId" descending
        String descResponse = mockMvc.perform(MockMvcRequestBuilders.get("/api/years")
                        .param("sortDirection", "desc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Year> descList = objectMapper.readValue(descResponse, YearResponse.class).getContent();

        assert !ascList.isEmpty();
        assert !descList.isEmpty();
        assert ascList.size() == descList.size();

        if(ascList.size() > 1) {
            assert(ascList.get(0) != descList.get(0));
        }
    }

    // Test sortBy Year

    // Test Page

    // Test PageSize


    //TODO
    // Test incorrect responses - error handling


}

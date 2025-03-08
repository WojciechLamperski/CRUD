package com.example.backend.controller;

import com.example.backend.entity.VoivodeshipEntity;
import com.example.backend.model.VoivodeshipModel;
import com.example.backend.model.VoivodeshipResponse;
import com.example.backend.service.VoivodeshipService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// Loads only the controller layer
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class VoivodeshipRestControllerTest {

    private MockMvc mockMvc;  // MockMvc to simulate HTTP requests

    @Mock
    private VoivodeshipService voivodeshipService;  // Mock the VoivodeshipService

    @InjectMocks
    private VoivodeshipRestController voivodeshipRestController;  // Inject the mock service into the controller

    @BeforeEach
    void setUp() {
        // Initialize MockMvc manually (no need for @Autowired)
        mockMvc = MockMvcBuilders.standaloneSetup(voivodeshipRestController).build();
    }

    @Test
    void givenMockedVoivodeshipInDatabase_WhenGetRequestIsMade_ThenReturnResponseWithMockedVoivodeship() throws Exception {
        // Setup mock behavior
        VoivodeshipModel voivodeshipModel = new VoivodeshipModel();
        voivodeshipModel.setVoivodeship("Wielkopolska");

        // TODO make them variables that you reference below, instead of repeating yourself 2 times
        VoivodeshipResponse voivodeshipResponse = new VoivodeshipResponse();
        voivodeshipResponse.setContent(List.of(voivodeshipModel));
        voivodeshipResponse.setPageNumber(0);
        voivodeshipResponse.setPageSize(1);
        voivodeshipResponse.setTotalElements(1);
        voivodeshipResponse.setTotalPages(1);
        voivodeshipResponse.setLast(true);

        // Mock service response
        when(voivodeshipService.findAll(0, 20, "voivodeshipId", "asc")).thenReturn(voivodeshipResponse);

        // Perform the GET request using MockMvc
        mockMvc.perform(get("/api/voivodeships")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))

                // Validate response structure
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].voivodeship").value("Wielkopolska"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].voivodeshipId").value(0))

                // Validate pagination properties
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last").value(true));
    }

    // findByID
    @Test
    void givenMockedVoivodeshipInDatabase_WhenGetRequestIsMade_ThenReturnMockedVoivodeship() throws Exception {
        // Setup mock behavior
        int mockVoivodeshipId = 0;

        VoivodeshipModel voivodeshipModel = new VoivodeshipModel();
        voivodeshipModel.setVoivodeship("Wielkopolska");

        when(voivodeshipService.findById(mockVoivodeshipId)).thenReturn(voivodeshipModel);

        // Perform the GET request using MockMvc
        mockMvc.perform(get("/api/voivodeships/{id}", mockVoivodeshipId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Ensure response status is 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.voivodeshipId").value(mockVoivodeshipId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.voivodeship").value("Wielkopolska")); // Ensure correct voivodeship value
    }

    @Test
    void givenNoVoivodeshipInDatabase_WhenPostRequestIsMade_ThenReturnAddedVoivodeship() throws Exception {
        // Setup mock behavior
        VoivodeshipEntity inputVoivodeshipEntity = new VoivodeshipEntity();
        inputVoivodeshipEntity.setVoivodeship("Wielkopolska");

        VoivodeshipModel outputVoivodeshipModel = new VoivodeshipModel();
        outputVoivodeshipModel.setVoivodeship("Wielkopolska");
        outputVoivodeshipModel.setVoivodeshipId(1);

        when(voivodeshipService.save(inputVoivodeshipEntity)).thenReturn(outputVoivodeshipModel);

        // Perform the GET request using MockMvc
        mockMvc.perform(post("/api/voivodeships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputVoivodeshipEntity)))
                .andExpect(status().isCreated())  // Ensure response status is 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.voivodeship").value("Wielkopolska")) // Ensure correct voivodeship value
                .andExpect(MockMvcResultMatchers.jsonPath("$.voivodeshipId").value(1));

    }

    @Test
    void givenVoivodeshipInDatabase_WhenPutRequestIsMade_ThenReturnUpdatedVoivodeship() throws Exception {
        // Setup mock behavior
        VoivodeshipEntity updatedVoivodeshipEntity = new VoivodeshipEntity();
        updatedVoivodeshipEntity.setVoivodeshipId(1);
        updatedVoivodeshipEntity.setVoivodeship("Mazowsze");

        VoivodeshipModel updatedVoivodeshipModel = new VoivodeshipModel();
        updatedVoivodeshipModel.setVoivodeshipId(1);
        updatedVoivodeshipModel.setVoivodeship("Mazowsze");

        when(voivodeshipService.save(updatedVoivodeshipEntity)).thenReturn(updatedVoivodeshipModel);

        // Perform the PUT request using MockMvc
        mockMvc.perform(put("/api/voivodeships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedVoivodeshipModel)))
                .andExpect(status().isOk())  // Ensure response status is 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.voivodeshipId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.voivodeship").value("Mazowsze")); // Ensure correct updated voivodeship value
    }

    @Test
    void givenVoivodeshipInDatabase_WhenDeleteRequestIsMade_ThenReturnNoVoivodeships() throws Exception {
        // Setup mock behavior
        int voivodeshipIdToDelete = 1;
        when(voivodeshipService.delete(voivodeshipIdToDelete)).thenReturn("Voivodeship deleted successfully");

        // Perform the DELETE request using MockMvc
        mockMvc.perform(delete("/api/voivodeships/{voivodeshipId}", voivodeshipIdToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());  // Ensure response status is 204 No Content
    }

    @Test
    void givenMultipleVoivodeshipsInDatabase_WhenGetRequestWithSortingAndPagingIsMade_ThenReturnPagedAndSortedResponse() throws Exception {
        // Setup mock behavior
        VoivodeshipModel voivodeshipModel1 = new VoivodeshipModel();
        voivodeshipModel1.setVoivodeshipId(1);
        voivodeshipModel1.setVoivodeship("Wielkopolska");

        VoivodeshipModel voivodeshipModel2 = new VoivodeshipModel();
        voivodeshipModel2.setVoivodeshipId(2);
        voivodeshipModel2.setVoivodeship("Mazowsze");

        VoivodeshipResponse voivodeshipResponse = new VoivodeshipResponse();
        voivodeshipResponse.setContent(List.of(voivodeshipModel2)); // Expecting only the highest voivodeship ("Mazowsze") due to sorting
        voivodeshipResponse.setPageNumber(0);
        voivodeshipResponse.setPageSize(1);
        voivodeshipResponse.setTotalElements(2);
        voivodeshipResponse.setTotalPages(2);
        voivodeshipResponse.setLast(false); // Since we are limiting to 1 per page, it's not the last page

        // Mock service response
        when(voivodeshipService.findAll(0, 1, "voivodeship", "desc")).thenReturn(voivodeshipResponse);

        // Perform the GET request using MockMvc
        mockMvc.perform(get("/api/voivodeships")
                        .param("pageNumber", "0")
                        .param("pageSize", "1")
                        .param("sortBy", "voivodeship")
                        .param("sortDirection", "desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Ensure response status is 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].voivodeship").value("Mazowsze")) // Expecting highest voivodeship due to desc sorting
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last").value(false)); // Not the last page
    }
}

package com.example.backend.controller;

import com.example.backend.entity.YearEntity;
import com.example.backend.model.YearModel;
import com.example.backend.model.YearResponse;
import com.example.backend.service.YearService; // Assuming you have a service to be mocked
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// Loads only the controller layer
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class YearRestControllerTest {

    private MockMvc mockMvc;  // MockMvc to simulate HTTP requests

    @Mock
    private YearService yearService;  // Mock the YearService

    @InjectMocks
    private YearRestController yearRestController;  // Inject the mock service into the controller

    @BeforeEach
    void setUp() {
        // Initialize MockMvc manually (no need for @Autowired)
        mockMvc = MockMvcBuilders.standaloneSetup(yearRestController).build();
    }

    // TODO Change the name
    @Test
    void givenAllYears_WhenGetRequestIsMade_ThenReturnYearList() throws Exception {
        // Setup mock behavior
        YearModel yearModel = new YearModel();
        yearModel.setYear(2025);

        YearResponse yearResponse = new YearResponse();
        yearResponse.setContent(List.of(yearModel));
        yearResponse.setPageNumber(0);
        yearResponse.setPageSize(1);
        yearResponse.setTotalElements(1);
        yearResponse.setTotalPages(1);
        yearResponse.setLast(true);

        // Mock service response
        when(yearService.findAll(0, 20, "yearId", "asc")).thenReturn(yearResponse);

        // Perform the GET request using MockMvc
        mockMvc.perform(get("/api/years")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))

                // Validate response structure
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].year").value(2025))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].yearId").isNumber())

                // Validate pagination properties
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.last").value(true));
    }


//    @Test
//    void givenYear_WhenPostRequestIsMade_ThenYearIsCreated() throws Exception {
//        // Setup mock behavior for POST request
//        YearModel newYear = new YearModel();
//        newYear.setYear(2025);
//
//        when(yearService.save(newYear)).thenReturn(newYear);  // Assuming createYear() exists in YearService
//
//        // Perform the POST request using MockMvc
//        mockMvc.perform(post("/api/years")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"year\": 2025}"))
//                .andExpect(status().isCreated())  // Assert that status is Created
//                .andExpect(jsonPath("$.year").value(2025));  // Assert the created year value
//    }
//
//    @Test
//    void givenYear_WhenDeleteRequestIsMade_ThenYearIsDeleted() throws Exception {
//        // Setup mock behavior for DELETE request
//        int yearId = 1;  // Assume we're deleting year with ID = 1
//        when(yearService.delete(yearId)).thenReturn(true);  // Assuming deleteYear() exists in YearService
//
//        // Perform the DELETE request using MockMvc
//        mockMvc.perform(delete("/api/years/{id}", yearId))
//                .andExpect(status().isNoContent());  // Assert that status is No Content (204)
//    }
}

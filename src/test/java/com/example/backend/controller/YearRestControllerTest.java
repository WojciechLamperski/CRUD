//package com.example.backend.controller;
//
//import com.example.backend.entity.YearEntity;
//import com.example.backend.model.YearModel;
//import com.example.backend.model.YearRequest;
//import com.example.backend.model.YearResponse;
//import com.example.backend.service.YearService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.List;
//
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//// Loads only the controller layer
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//public class YearRestControllerTest {
//
//    private MockMvc mockMvc;  // MockMvc to simulate HTTP requests
//
//    @Mock
//    private YearService yearService;  // Mock the YearService
//
//    @InjectMocks
//    private YearRestController yearRestController;  // Inject the mock service into the controller
//
//    @BeforeEach
//    void setUp() {
//        // Initialize MockMvc manually (no need for @Autowired)
//        mockMvc = MockMvcBuilders.standaloneSetup(yearRestController).build();
//    }
//
//    @Test
//    void givenMockedYearInDatabase_WhenGetRequestIsMade_ThenReturnResponseWithMockedYear() throws Exception {
//        // Setup mock behavior
//        YearModel yearModel = new YearModel();
//        yearModel.setYear(2025);
//
//        // TODO make them variables that you reference below, instead of repeating yourself 2 times
//        YearResponse yearResponse = new YearResponse();
//        yearResponse.setContent(List.of(yearModel));
//        yearResponse.setPageNumber(0);
//        yearResponse.setPageSize(1);
//        yearResponse.setTotalElements(1);
//        yearResponse.setTotalPages(1);
//        yearResponse.setLast(true);
//
//        // Mock service response
//        when(yearService.findAll(0, 20, "yearId", "asc")).thenReturn(yearResponse);
//
//        // Perform the GET request using MockMvc
//        mockMvc.perform(get("/api/years")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//
//                // Validate response structure
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].year").value(2025))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].yearId").value(0))
//
//                // Validate pagination properties
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.last").value(true));
//    }
//
//    // findByID
//    @Test
//    void givenMockedYearInDatabase_WhenGetRequestIsMade_ThenReturnMockedYear() throws Exception {
//        // Setup mock behavior
//        int mockYearId = 0;
//
//        YearModel yearModel = new YearModel();
//        yearModel.setYear(2025);
//
//        when(yearService.findById(mockYearId)).thenReturn(yearModel);
//
//        // Perform the GET request using MockMvc
//        mockMvc.perform(get("/api/years/{id}", mockYearId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())  // Ensure response status is 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.yearId").value(mockYearId))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2025)); // Ensure correct year value
//    }
//
//    @Test
//    void givenNoYearInDatabase_WhenPostRequestIsMade_ThenReturnAddedYear() throws Exception {
//        // Setup mock behavior
//        YearRequest inputYearRequest = new YearRequest();
//        inputYearRequest.setYear(2025);
//
//        YearModel outputYearModel = new YearModel();
//        outputYearModel.setYear(2025);
//        outputYearModel.setYearId(1);
//
//        when(yearService.save(inputYearRequest)).thenReturn(outputYearModel);
//
//        // Perform the GET request using MockMvc
//        mockMvc.perform(post("/api/years")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(inputYearRequest)))
//                .andExpect(status().isCreated())  // Ensure response status is 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2025)) // Ensure correct year value
//                .andExpect(MockMvcResultMatchers.jsonPath("$.yearId").value(1));
//
//    }
//
//    @Test
//    void givenYearInDatabase_WhenPutRequestIsMade_ThenReturnUpdatedYear() throws Exception {
//        // Setup mock behavior
//        YearRequest updatedYearRequest = new YearRequest();
//        updatedYearRequest.setYearId(1);
//        updatedYearRequest.setYear(2030);
//
//        YearEntity updatedYearEntity = new YearEntity();
//        updatedYearEntity.setYear(2025);
//        updatedYearEntity.setYearId(1);
//
//        YearModel updatedYearModel = new YearModel();
//        updatedYearModel.setYearId(1);
//        updatedYearModel.setYear(2030);
//
//        when(yearService.save(updatedYearRequest)).thenReturn(updatedYearModel);
//
//        // Perform the PUT request using MockMvc
//        mockMvc.perform(put("/api/years")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(updatedYearEntity)))
//                .andExpect(status().isOk())  // Ensure response status is 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.yearId").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2030)); // Ensure correct updated year value
//    }
//
//    @Test
//    void givenYearInDatabase_WhenDeleteRequestIsMade_ThenReturnNoYears() throws Exception {
//        // Setup mock behavior
//        int yearIdToDelete = 1;
//
//        doNothing().when(yearService).delete(yearIdToDelete);
//
//        // Perform the DELETE request using MockMvc
//        mockMvc.perform(delete("/api/years/{yearId}", yearIdToDelete)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());  // Ensure response status is 204 No Content
//    }
//
//    @Test
//    void givenMultipleYearsInDatabase_WhenGetRequestWithSortingAndPagingIsMade_ThenReturnPagedAndSortedResponse() throws Exception {
//        // Setup mock behavior
//        YearModel yearModel1 = new YearModel();
//        yearModel1.setYearId(1);
//        yearModel1.setYear(2025);
//
//        YearModel yearModel2 = new YearModel();
//        yearModel2.setYearId(2);
//        yearModel2.setYear(2030);
//
//        YearResponse yearResponse = new YearResponse();
//        yearResponse.setContent(List.of(yearModel2)); // Expecting only the highest year (2030) due to sorting
//        yearResponse.setPageNumber(0);
//        yearResponse.setPageSize(1);
//        yearResponse.setTotalElements(2);
//        yearResponse.setTotalPages(2);
//        yearResponse.setLast(false); // Since we are limiting to 1 per page, it's not the last page
//
//        // Mock service response
//        when(yearService.findAll(0, 1, "year", "desc")).thenReturn(yearResponse);
//
//        // Perform the GET request using MockMvc
//        mockMvc.perform(get("/api/years")
//                        .param("pageNumber", "0")
//                        .param("pageSize", "1")
//                        .param("sortBy", "year")
//                        .param("sortDirection", "desc")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())  // Ensure response status is 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].year").value(2030)) // Expecting highest year due to desc sorting
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.last").value(false)); // Not the last page
//    }
//}

//package com.example.backend.controller;
//
//import com.example.backend.entity.DistrictEntity;
//import com.example.backend.entity.VoivodeshipEntity;
//import com.example.backend.model.DistrictModel;
//import com.example.backend.model.DistrictResponse;
//import com.example.backend.service.DistrictService;
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
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//// Loads only the controller layer
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//public class DistrictRestControllerTest {
//
//    private MockMvc mockMvc;  // MockMvc to simulate HTTP requests
//
//    @Mock
//    private DistrictService districtService;  // Mock the DistrictService
//
//    @InjectMocks
//    private DistrictRestController districtRestController;  // Inject the mock service into the controller
//
//    @BeforeEach
//    void setUp() {
//        // Initialize MockMvc manually (no need for @Autowired)
//        mockMvc = MockMvcBuilders.standaloneSetup(districtRestController).build();
//    }
//
//    // GET
//    @Test
//    void givenMockedDistrictInDatabase_WhenGetRequestIsMade_ThenReturnResponseWithMockedDistrict() throws Exception {
//        // Setup mock behavior
//        DistrictModel districtModel = new DistrictModel();
//        districtModel.setDistrict("poznański");
//
//        // TODO make them variables that you reference below, instead of repeating yourself 2 times
//        DistrictResponse districtResponse = new DistrictResponse();
//        districtResponse.setContent(List.of(districtModel));
//        districtResponse.setPageNumber(0);
//        districtResponse.setPageSize(1);
//        districtResponse.setTotalElements(1);
//        districtResponse.setTotalPages(1);
//        districtResponse.setLast(true);
//
//        // Mock service response
//        when(districtService.findAll(0, 20, "districtId", "asc")).thenReturn(districtResponse);
//
//        // Perform the GET request using MockMvc
//        mockMvc.perform(get("/api/districts")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//
//                // Validate response structure
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].district").value("poznański"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].districtId").value(0))
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
//    void givenMockedDistrictInDatabase_WhenGetRequestIsMade_ThenReturnMockedDistrict() throws Exception {
//        // Setup mock behavior
//        int mockDistrictId = 0;
//
//        DistrictModel districtModel = new DistrictModel();
//        districtModel.setDistrict("poznański");
//
//        when(districtService.findById(mockDistrictId)).thenReturn(districtModel);
//
//        // Perform the GET request using MockMvc
//        mockMvc.perform(get("/api/districts/{id}", mockDistrictId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())  // Ensure response status is 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.districtId").value(mockDistrictId))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.district").value("poznański")); // Ensure correct district value
//    }
//
//    // POST
//    @Test
//    void givenNoDistrictInDatabase_WhenPostRequestIsMade_ThenReturnAddedDistrict() throws Exception {
//        // Setup mock behavior
//        DistrictEntity inputDistrictEntity = new DistrictEntity();
//        inputDistrictEntity.setDistrict("poznański");
//        inputDistrictEntity.setVoivodeshipId(1);
//
//        DistrictModel outputDistrictModel = new DistrictModel();
//        outputDistrictModel.setDistrict("poznański");
//        outputDistrictModel.setVoivodeship("WIELKOPOLSKA");
//        outputDistrictModel.setDistrictId(1);
//
//        when(districtService.save(inputDistrictEntity)).thenReturn(outputDistrictModel);
//
//        // Perform the GET request using MockMvc
//        mockMvc.perform(post("/api/districts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(inputDistrictEntity)))
//                .andExpect(status().isCreated())  // Ensure response status is 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.districtId").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.district").value("poznański")) // Ensure correct district value
//                .andExpect(MockMvcResultMatchers.jsonPath("$.voivodeship").value("WIELKOPOLSKA"));
//
//
//    }
//
//    // PUT
//    @Test
//    void givenDistrictInDatabase_WhenPutRequestIsMade_ThenReturnUpdatedDistrict() throws Exception {
//        // Setup mock behavior
//        DistrictEntity updatedDistrictEntity = new DistrictEntity();
//        updatedDistrictEntity.setDistrictId(1);
//        updatedDistrictEntity.setDistrict("warszawski");
//
//        DistrictModel updatedDistrictModel = new DistrictModel();
//        updatedDistrictModel.setDistrictId(1);
//        updatedDistrictModel.setDistrict("warszawski");
//
//        when(districtService.save(updatedDistrictEntity)).thenReturn(updatedDistrictModel);
//
//        // Perform the PUT request using MockMvc
//        mockMvc.perform(put("/api/districts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(updatedDistrictModel)))
//                .andExpect(status().isOk())  // Ensure response status is 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.districtId").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.district").value("warszawski")); // Ensure correct updated district value
//    }
//
//    // DELETE
//    @Test
//    void givenDistrictInDatabase_WhenDeleteRequestIsMade_ThenReturnNoDistricts() throws Exception {
//        // Setup mock behavior
//        int districtIdToDelete = 1;
//        when(districtService.delete(districtIdToDelete)).thenReturn("District deleted successfully");
//
//        // Perform the DELETE request using MockMvc
//        mockMvc.perform(delete("/api/districts/{districtId}", districtIdToDelete)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());  // Ensure response status is 204 No Content
//    }
//
//    @Test
//    void givenMultipleDistrictsInDatabase_WhenGetRequestWithSortingAndPagingIsMade_ThenReturnPagedAndSortedResponse() throws Exception {
//        // Setup mock behavior
//        DistrictModel districtModel1 = new DistrictModel();
//        districtModel1.setDistrictId(1);
//        districtModel1.setDistrict("poznański");
//
//        DistrictModel districtModel2 = new DistrictModel();
//        districtModel2.setDistrictId(2);
//        districtModel2.setDistrict("warszawski");
//
//        DistrictResponse districtResponse = new DistrictResponse();
//        districtResponse.setContent(List.of(districtModel2)); // Expecting only the highest district ("warszawski") due to sorting
//        districtResponse.setPageNumber(0);
//        districtResponse.setPageSize(1);
//        districtResponse.setTotalElements(2);
//        districtResponse.setTotalPages(2);
//        districtResponse.setLast(false); // Since we are limiting to 1 per page, it's not the last page
//
//        // Mock service response
//        when(districtService.findAll(0, 1, "district", "desc")).thenReturn(districtResponse);
//
//        // Perform the GET request using MockMvc
//        mockMvc.perform(get("/api/districts")
//                        .param("pageNumber", "0")
//                        .param("pageSize", "1")
//                        .param("sortBy", "district")
//                        .param("sortDirection", "desc")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())  // Ensure response status is 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].district").value("warszawski")) // Expecting highest district due to desc sorting
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.last").value(false)); // Not the last page
//    }
//
//    @Test
//    void givenVoivodeshipId_WhenGetRequestIsMade_ThenReturnDistrictsInThatVoivodeship() throws Exception {
//        // Setup mock data
//        int voivodeshipId = 1;
//
//        DistrictModel districtModel1 = new DistrictModel();
//        districtModel1.setDistrictId(101);
//        districtModel1.setDistrict("District A");
//
//        DistrictModel districtModel2 = new DistrictModel();
//        districtModel2.setDistrictId(102);
//        districtModel2.setDistrict("District B");
//
//        DistrictResponse districtResponse = new DistrictResponse();
//        districtResponse.setContent(List.of(districtModel1, districtModel2));
//        districtResponse.setPageNumber(0);
//        districtResponse.setPageSize(2);
//        districtResponse.setTotalElements(2);
//        districtResponse.setTotalPages(1);
//        districtResponse.setLast(true);
//
//        // Mock service response
//        when(districtService.findAllInVoivodeship(voivodeshipId, 0, 20, "districtId", "asc"))
//                .thenReturn(districtResponse);
//
//        // Perform GET request
//        mockMvc.perform(get("/api/voivodeships/{voivodeshipId}/districts", voivodeshipId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//
//                // Validate response content
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].districtId").value(101))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].district").value("District A"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].districtId").value(102))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].district").value("District B"))
//
//                // Validate pagination properties
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.last").value(true));
//    }
//
//}

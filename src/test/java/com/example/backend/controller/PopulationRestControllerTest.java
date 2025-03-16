//package com.example.backend.controller;
//
//import com.example.backend.entity.PopulationEntity;
//import com.example.backend.model.*;
//import com.example.backend.service.PopulationService;
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
//public class PopulationRestControllerTest {
//
//    private MockMvc mockMvc;  // MockMvc to simulate HTTP requests
//
//    @Mock
//    private PopulationService populationService;  // Mock the PopulationService
//
//    @InjectMocks
//    private PopulationRestController populationRestController;  // Inject the mock service into the controller
//
//    @BeforeEach
//    void setUp() {
//        // Initialize MockMvc manually (no need for @Autowired)
//        mockMvc = MockMvcBuilders.standaloneSetup(populationRestController).build();
//    }
//
//    @Test
//    void givenMockedPopulationInDatabase_WhenGetRequestIsMade_ThenReturnResponseWithMockedPopulation() throws Exception {
//        // Setup mock behavior
//
//        DistrictModel districtModel = new DistrictModel();
//        districtModel.setDistrict("poznański");
//        districtModel.setVoivodeship("Wielkopolska");
//
//        PopulationModel populationModel = new PopulationModel();
//        populationModel.setDistrict(districtModel);
//        populationModel.setMen(2500);
//        populationModel.setWomen(2400);
//        populationModel.setYear(2025);
//
//
//        // TODO make them variables that you reference below, instead of repeating yourself 2 times
//        PopulationResponse populationResponse = new PopulationResponse();
//        populationResponse.setContent(List.of(populationModel));
//        populationResponse.setPageNumber(0);
//        populationResponse.setPageSize(1);
//        populationResponse.setTotalElements(1);
//        populationResponse.setTotalPages(1);
//        populationResponse.setLast(true);
//
//        // Mock service response
//        when(populationService.findAll(0, 20, "populationId", "asc")).thenReturn(populationResponse);
//
//        // Perform the GET request using MockMvc
//        mockMvc.perform(get("/api/populations")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//
//                // Validate response structure
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].populationId").value(0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].men").value(2500))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].women").value(2400))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].year").value(2025))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].district.districtId").value(0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].district.district").value("poznański"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].district.voivodeship").value("Wielkopolska"))
//
//                // Validate pagination properties
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.last").value(true));
//    }
//
//    @Test
//    void givenMockedPopulationInDatabase_WhenFindByIdRequestIsMade_ThenReturnMockedPopulation() throws Exception {
//        // Given
//        int populationId = 1;
//
//        DistrictModel districtModel = new DistrictModel();
//        districtModel.setDistrict("poznański");
//        districtModel.setVoivodeship("Wielkopolska");
//
//        PopulationModel populationModel = new PopulationModel();
//        populationModel.setPopulationId(populationId);
//        populationModel.setDistrict(districtModel);
//        populationModel.setMen(2500);
//        populationModel.setWomen(2400);
//        populationModel.setYear(2025);
//
//        // Mock service response
//        when(populationService.findById(populationId)).thenReturn(populationModel);
//
//        // When & Then
//        mockMvc.perform(get("/api/populations/{populationId}", populationId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.populationId").value(populationId))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.men").value(2500))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.women").value(2400))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2025))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.district.district").value("poznański"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.district.voivodeship").value("Wielkopolska"));
//    }
//
//    @Test
//    void givenNoPopulationInDatabase_WhenPostRequestIsMade_ThenReturnAddedPopulation() throws Exception {
//        // Given
//        DistrictModel districtModel = new DistrictModel();
//        districtModel.setDistrict("poznański");
//        districtModel.setVoivodeship("Wielkopolska");
//
//        PopulationEntity populationEntity = new PopulationEntity();
//        populationEntity.setPopulationId(0);  // Ensures creation (not update)
//        populationEntity.setMen(2500);
//        populationEntity.setWomen(2400);
//        populationEntity.setYearId(1);
//        populationEntity.setDistrictId(1);
//
//        PopulationModel populationModel = new PopulationModel();
//        populationModel.setPopulationId(1);  // Assume it gets assigned ID 1 after saving
//        populationModel.setMen(2500);
//        populationModel.setWomen(2400);
//        populationModel.setYear(2025);
//        populationModel.setDistrict(districtModel);
//
//
//        // Mock service response
//        when(populationService.save(populationEntity)).thenReturn(populationModel);
//
//        // When & Then
//        mockMvc.perform(post("/api/populations")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(populationEntity)))
//                .andExpect(status().isCreated())  // HTTP 201 Created
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.populationId").value(1))  // ID assigned after saving
//                .andExpect(MockMvcResultMatchers.jsonPath("$.men").value(2500))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.women").value(2400))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2025))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.district.district").value("poznański"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.district.voivodeship").value("Wielkopolska"));
//    }
//
//    @Test
//    void givenPopulationInDatabase_WhenPutRequestIsMade_ThenReturnUpdatedPopulation() throws Exception {
//        // Given
//        DistrictModel districtModel = new DistrictModel();
//        districtModel.setDistrict("poznański");
//        districtModel.setVoivodeship("Wielkopolska");
//
//        PopulationEntity populationEntity = new PopulationEntity();
//        populationEntity.setPopulationId(0);  // Ensures creation (not update)
//        populationEntity.setMen(2500);
//        populationEntity.setWomen(2400);
//        populationEntity.setYearId(1);
//        populationEntity.setDistrictId(1);
//
//        PopulationModel populationModel = new PopulationModel();
//        populationModel.setPopulationId(1);  // Assume it gets assigned ID 1 after saving
//        populationModel.setMen(2500);
//        populationModel.setWomen(2400);
//        populationModel.setYear(2025);
//        populationModel.setDistrict(districtModel);
//
//
//        // Mock service response
//        when(populationService.save(populationEntity)).thenReturn(populationModel);
//
//        // When & Then
//        mockMvc.perform(put("/api/populations")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(populationEntity)))
//                .andExpect(status().isOk())  // HTTP 201 Created
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.populationId").value(1))  // ID assigned after saving
//                .andExpect(MockMvcResultMatchers.jsonPath("$.men").value(2500))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.women").value(2400))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2025))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.district.district").value("poznański"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.district.voivodeship").value("Wielkopolska"));
//    }
//
//    // DELETE
//    @Test
//    void givenPopulationInDatabase_WhenDeleteRequestIsMade_ThenReturnNoPopulations() throws Exception {
//        // Setup mock behavior
//        int populationIdToDelete = 1;
//        when(populationService.delete(populationIdToDelete)).thenReturn("Population deleted successfully");
//
//        // Perform the DELETE request using MockMvc
//        mockMvc.perform(delete("/api/populations/{populationId}", populationIdToDelete)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());  // Ensure response status is 204 No Content
//    }
//
//    @Test
//    void givenDistrictId_WhenGetRequestIsMade_ThenReturnPopulationsInThatDistrict() throws Exception {
//        // Setup mock data
//        int districtId = 1;
//
//        DistrictModel districtModel = new DistrictModel();
//        districtModel.setDistrictId(districtId);
//        districtModel.setDistrict("poznański");
//        districtModel.setVoivodeship("Wielkopolska");
//
//        PopulationModel populationModel = new PopulationModel();
//        populationModel.setPopulationId(1);
//        populationModel.setMen(2500);
//        populationModel.setWomen(2400);
//        populationModel.setYear(2025);
//        populationModel.setDistrict(districtModel);
//
//        PopulationResponse populationResponse = new PopulationResponse();
//        populationResponse.setContent(List.of(populationModel));
//        populationResponse.setPageNumber(0);
//        populationResponse.setPageSize(1);
//        populationResponse.setTotalElements(1);
//        populationResponse.setTotalPages(1);
//        populationResponse.setLast(true);
//
//        // Mock service response
//        when(populationService.findAllInDistrict(districtId, 0, 20, "populationId", "asc"))
//                .thenReturn(populationResponse);
//
//        // Perform GET request
//        mockMvc.perform(get("/api/districts/{districtId}/populations", districtId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].populationId").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].men").value(2500))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].women").value(2400))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].year").value(2025))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].district.district").value("poznański"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.pageNumber").value(0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1));
//    }
//
//    @Test
//    void givenYearId_WhenGetRequestIsMade_ThenReturnPopulationsForThatYear() throws Exception {
//        int yearId = 2025;
//
//        PopulationModel populationModel = new PopulationModel();
//        populationModel.setPopulationId(1);
//        populationModel.setMen(2500);
//        populationModel.setWomen(2400);
//        populationModel.setYear(yearId);
//
//        PopulationResponse populationResponse = new PopulationResponse();
//        populationResponse.setContent(List.of(populationModel));
//        populationResponse.setPageNumber(0);
//        populationResponse.setPageSize(1);
//        populationResponse.setTotalElements(1);
//        populationResponse.setTotalPages(1);
//        populationResponse.setLast(true);
//
//        when(populationService.findAllInYear(yearId, 0, 20, "populationId", "asc"))
//                .thenReturn(populationResponse);
//
//        mockMvc.perform(get("/api/years/{yearId}/populations", yearId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].year").value(yearId));
//    }
//
//    @Test
//    void givenVoivodeshipId_WhenGetRequestIsMade_ThenReturnPopulationsForThatVoivodeship() throws Exception {
//        int voivodeshipId = 1;
//
//        PopulationModel populationModel = new PopulationModel();
//        populationModel.setPopulationId(1);
//        populationModel.setMen(2500);
//        populationModel.setWomen(2400);
//        populationModel.setYear(2025);
//
//        PopulationResponse populationResponse = new PopulationResponse();
//        populationResponse.setContent(List.of(populationModel));
//        populationResponse.setPageNumber(0);
//        populationResponse.setPageSize(1);
//        populationResponse.setTotalElements(1);
//        populationResponse.setTotalPages(1);
//        populationResponse.setLast(true);
//
//        when(populationService.findAllInVoivodeship(voivodeshipId, 0, 20, "populationId", "asc"))
//                .thenReturn(populationResponse);
//
//        mockMvc.perform(get("/api/voivodeships/{voivodeshipId}/populations", voivodeshipId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].populationId").value(1));
//    }
//
//}

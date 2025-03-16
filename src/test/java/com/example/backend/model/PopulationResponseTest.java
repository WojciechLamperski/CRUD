package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class PopulationResponseTest {

    private static final int pageNumber = 1;
    private static final int pageSize = 20;
    private static final long totalElements = 100L;
    private static final int totalPages = 5;
    private static final boolean last = true;
    private static final PopulationModel populationModel = new PopulationModel();

    static {
        populationModel.setPopulationId(1);
        populationModel.setYear(2025);
        populationModel.setDistrict(new DistrictModel());
        populationModel.setMen(500);
        populationModel.setWomen(450);
    }

    @Test
    public void testObjectGetters() {
        PopulationResponse populationResponse = new PopulationResponse(
                List.of(populationModel), pageNumber, pageSize, totalElements, totalPages, last);

        assertEquals(pageNumber, populationResponse.getPageNumber());
        assertEquals(pageSize, populationResponse.getPageSize());
        assertEquals(totalElements, populationResponse.getTotalElements());
        assertEquals(totalPages, populationResponse.getTotalPages());
        assertTrue(populationResponse.isLast());
        assertEquals(1, populationResponse.getContent().size());
        assertEquals(populationModel, populationResponse.getContent().getFirst());
    }

    @Test
    public void testObjectIsReflexive() {
        PopulationResponse populationResponse = new PopulationResponse(
                List.of(populationModel), pageNumber, pageSize, totalElements, totalPages, last);

        assertEquals(populationResponse, populationResponse);
    }

    @Test
    public void testSetters() {
        PopulationResponse populationResponse = new PopulationResponse();
        populationResponse.setContent(List.of(populationModel));
        populationResponse.setPageNumber(pageNumber);
        populationResponse.setPageSize(pageSize);
        populationResponse.setTotalElements(totalElements);
        populationResponse.setTotalPages(totalPages);
        populationResponse.setLast(last);

        assertEquals(pageNumber, populationResponse.getPageNumber());
        assertEquals(pageSize, populationResponse.getPageSize());
        assertEquals(totalElements, populationResponse.getTotalElements());
        assertEquals(totalPages, populationResponse.getTotalPages());
        assertTrue(populationResponse.isLast());
        assertEquals(1, populationResponse.getContent().size());
        assertEquals(populationModel, populationResponse.getContent().getFirst());
    }
}

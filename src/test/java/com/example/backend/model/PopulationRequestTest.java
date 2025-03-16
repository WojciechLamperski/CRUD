package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PopulationRequestTest {

    private static final Integer populationId = 1;
    private static final Integer yearId = 2023;
    private static final Integer districtId = 10;
    private static final int men = 5000;
    private static final int women = 5500;

    @Test
    public void testObjectGetters() {
        PopulationRequest populationRequest = new PopulationRequest();
        populationRequest.setPopulationId(populationId);
        populationRequest.setYearId(yearId);
        populationRequest.setDistrictId(districtId);
        populationRequest.setMen(men);
        populationRequest.setWomen(women);

        assertEquals(populationId, populationRequest.getPopulationId());
        assertEquals(yearId, populationRequest.getYearId());
        assertEquals(districtId, populationRequest.getDistrictId());
        assertEquals(men, populationRequest.getMen());
        assertEquals(women, populationRequest.getWomen());
    }

    @Test
    public void testObjectIsReflexive() {
        PopulationRequest populationRequest = new PopulationRequest();
        populationRequest.setPopulationId(populationId);
        populationRequest.setYearId(yearId);
        populationRequest.setDistrictId(districtId);
        populationRequest.setMen(men);
        populationRequest.setWomen(women);

        assertEquals(populationRequest, populationRequest);
    }
}
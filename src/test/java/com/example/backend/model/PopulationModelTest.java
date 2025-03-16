package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PopulationModelTest {

    private static final int populationId = 1;
    private static final int year = 2025;
    private static final int men = 1000;
    private static final int women = 1200;
    private static final DistrictModel district = new DistrictModel();

    @Test
    public void testObjectGetters() {
        PopulationModel populationModel = new PopulationModel();
        populationModel.setPopulationId(populationId);
        populationModel.setYear(year);
        populationModel.setDistrict(district);
        populationModel.setMen(men);
        populationModel.setWomen(women);

        assertEquals(populationId, populationModel.getPopulationId());
        assertEquals(year, populationModel.getYear());
        assertEquals(district, populationModel.getDistrict());
        assertEquals(men, populationModel.getMen());
        assertEquals(women, populationModel.getWomen());
    }

    @Test
    public void testObjectIsReflexive() {
        PopulationModel populationModel = new PopulationModel();
        populationModel.setPopulationId(populationId);
        populationModel.setYear(year);
        populationModel.setDistrict(district);
        populationModel.setMen(men);
        populationModel.setWomen(women);

        assertEquals(populationModel, populationModel);
    }
}

package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistrictModelTest {

    private static final int districtId = 1;
    private static final String district = "Warsaw";
    private static final String voivodeship = "Mazowieckie";

    @Test
    public void testObjectGetters() {
        DistrictModel districtModel = new DistrictModel();
        districtModel.setDistrictId(districtId);
        districtModel.setDistrict(district);
        districtModel.setVoivodeship(voivodeship);

        assertEquals(districtId, districtModel.getDistrictId());
        assertEquals(district, districtModel.getDistrict());
        assertEquals(voivodeship, districtModel.getVoivodeship());
    }

    @Test
    public void testObjectIsReflexive() {
        DistrictModel districtModel = new DistrictModel();
        districtModel.setDistrictId(districtId);
        districtModel.setDistrict(district);
        districtModel.setVoivodeship(voivodeship);

        assertEquals(districtModel, districtModel);
    }

}

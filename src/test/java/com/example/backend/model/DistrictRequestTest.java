package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DistrictRequestTest {

    private static final Integer districtId = 1;
    private static final Integer voivodeshipId = 10;
    private static final String district = "Warszawa";

    @Test
    public void testObjectGetters() {
        DistrictRequest districtRequest = new DistrictRequest();
        districtRequest.setDistrictId(districtId);
        districtRequest.setVoivodeshipId(voivodeshipId);
        districtRequest.setDistrict(district);

        assertEquals(districtId, districtRequest.getDistrictId());
        assertEquals(voivodeshipId, districtRequest.getVoivodeshipId());
        assertEquals(district, districtRequest.getDistrict());
    }

    @Test
    public void testObjectIsReflexive() {
        DistrictRequest districtRequest = new DistrictRequest();
        districtRequest.setDistrictId(districtId);
        districtRequest.setVoivodeshipId(voivodeshipId);
        districtRequest.setDistrict(district);

        assertTrue(districtRequest.equals(districtRequest));
    }
}
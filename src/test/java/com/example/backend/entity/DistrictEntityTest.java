package com.example.backend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DistrictEntityTest {

    private static final String districtName = "Warsaw";
    private static final int voivodeshipId = 1;

    @Test
    public void testObjectGetters_IdConstructor() {
        DistrictEntity districtEntity = new DistrictEntity();
        districtEntity.setDistrictId(1);
        districtEntity.setDistrict(districtName);
        districtEntity.setVoivodeshipId(voivodeshipId);

        assertEquals(1, districtEntity.getDistrictId());
        assertEquals(districtName, districtEntity.getDistrict());
        assertEquals(voivodeshipId, districtEntity.getVoivodeshipId());
    }

    @Test
    public void testObjectsAreEqual() {
        DistrictEntity districtEntity1 = new DistrictEntity();
        districtEntity1.setDistrict(districtName);
        districtEntity1.setVoivodeshipId(voivodeshipId);

        DistrictEntity districtEntity2 = new DistrictEntity();
        districtEntity2.setDistrict(districtName);
        districtEntity2.setVoivodeshipId(voivodeshipId);

        assertEquals(districtEntity1, districtEntity2);
    }

    @Test
    public void testEqualObjectsHaveSameHashcode() {
        DistrictEntity districtEntity1 = new DistrictEntity();
        districtEntity1.setDistrict(districtName);
        districtEntity1.setVoivodeshipId(voivodeshipId);

        DistrictEntity districtEntity2 = new DistrictEntity();
        districtEntity2.setDistrict(districtName);
        districtEntity2.setVoivodeshipId(voivodeshipId);

        assertEquals(districtEntity1.hashCode(), districtEntity2.hashCode());
    }

    @Test
    public void testObjectIsReflexive() {
        DistrictEntity districtEntity = new DistrictEntity();
        districtEntity.setDistrict(districtName);
        districtEntity.setVoivodeshipId(voivodeshipId);

        assertTrue(districtEntity.equals(districtEntity));
    }

    @Test
    public void testToString() {
        DistrictEntity districtEntity = new DistrictEntity();
        districtEntity.setDistrictId(1);
        districtEntity.setDistrict(districtName);
        districtEntity.setVoivodeshipId(voivodeshipId);

        String expToString = expectedToString(1, districtName, voivodeshipId);
        assertEquals(expToString, districtEntity.toString());
    }

    private String expectedToString(int id, String district, int voivodeshipId) {
        return "DistrictEntity(districtId=" + id + ", district=" + district + ", voivodeshipId=" + voivodeshipId + ", voivodeship=null, populations=null)";
    }
}

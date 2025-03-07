package com.example.backend.entity;

import com.example.backend.entity.PopulationEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationEntityTest {

    private static final int yearId = 2025;
    private static final int districtId = 1;
    private static final int men = 50000;
    private static final int women = 52000;

    @Test
    public void testObjectGetters_IdConstructor() {
        PopulationEntity populationEntity = new PopulationEntity();
        populationEntity.setPopulationId(1);
        populationEntity.setYearId(yearId);
        populationEntity.setDistrictId(districtId);
        populationEntity.setMen(men);
        populationEntity.setWomen(women);

        assertEquals(1, populationEntity.getPopulationId());
        assertEquals(yearId, populationEntity.getYearId());
        assertEquals(districtId, populationEntity.getDistrictId());
        assertEquals(men, populationEntity.getMen());
        assertEquals(women, populationEntity.getWomen());
    }

    @Test
    public void testObjectsAreEqual() {
        PopulationEntity populationEntity1 = new PopulationEntity();
        populationEntity1.setYearId(yearId);
        populationEntity1.setDistrictId(districtId);
        populationEntity1.setMen(men);
        populationEntity1.setWomen(women);

        PopulationEntity populationEntity2 = new PopulationEntity();
        populationEntity2.setYearId(yearId);
        populationEntity2.setDistrictId(districtId);
        populationEntity2.setMen(men);
        populationEntity2.setWomen(women);

        assertTrue(populationEntity1.equals(populationEntity2));
    }

    @Test
    public void testEqualObjectsHaveSameHashcode() {
        PopulationEntity populationEntity1 = new PopulationEntity();
        populationEntity1.setYearId(yearId);
        populationEntity1.setDistrictId(districtId);
        populationEntity1.setMen(men);
        populationEntity1.setWomen(women);

        PopulationEntity populationEntity2 = new PopulationEntity();
        populationEntity2.setYearId(yearId);
        populationEntity2.setDistrictId(districtId);
        populationEntity2.setMen(men);
        populationEntity2.setWomen(women);

        assertEquals(populationEntity1.hashCode(), populationEntity2.hashCode());
    }

    @Test
    public void testObjectIsReflexive() {
        PopulationEntity populationEntity = new PopulationEntity();
        populationEntity.setYearId(yearId);
        populationEntity.setDistrictId(districtId);
        populationEntity.setMen(men);
        populationEntity.setWomen(women);

        assertTrue(populationEntity.equals(populationEntity));
    }

    @Test
    public void testToString() {
        PopulationEntity populationEntity = new PopulationEntity();
        populationEntity.setPopulationId(1);
        populationEntity.setYearId(yearId);
        populationEntity.setDistrictId(districtId);
        populationEntity.setMen(men);
        populationEntity.setWomen(women);

        String expToString = expectedToString(1, yearId, districtId, men, women);
        assertEquals(expToString, populationEntity.toString());
    }

    private String expectedToString(int id, int yearId, int districtId, int men, int women) {
        return "PopulationEntity(populationId=" + id + ", yearId=" + yearId + ", districtId=" + districtId + ", men=" + men + ", women=" + women + ", district=null, year=null)";
    }
}


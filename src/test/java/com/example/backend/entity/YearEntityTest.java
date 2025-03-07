package com.example.backend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class YearEntityTest {

    private static final int year = 2025;

    @Test
    public void testObjectGetters_IdConstructor() {
        YearEntity yearEntity = new YearEntity();
        yearEntity.setYearId(1);
        yearEntity.setYear(year);

        assertEquals(1, yearEntity.getYearId());
        assertEquals(year, yearEntity.getYear());
    }

    @Test
    public void testObjectsAreEqual() {
        YearEntity yearEntity1 = new YearEntity();
        yearEntity1.setYear(year);

        YearEntity yearEntity2 = new YearEntity();
        yearEntity2.setYear(year);

        assertTrue(yearEntity1.equals(yearEntity2));
    }

    @Test
    public void testEqualObjectsHaveSameHashcode() {
        YearEntity yearEntity1 = new YearEntity();
        yearEntity1.setYear(year);

        YearEntity yearEntity2 = new YearEntity();
        yearEntity2.setYear(year);

        assertEquals(yearEntity1.hashCode(), yearEntity2.hashCode());
    }

    @Test
    public void testObjectIsReflexive() {
        YearEntity yearEntity = new YearEntity();
        yearEntity.setYear(year);

        assertTrue(yearEntity.equals(yearEntity));
    }

    @Test
    public void testToString() {
        YearEntity yearEntity = new YearEntity();
        yearEntity.setYearId(1);
        yearEntity.setYear(year);

        String expToString = expectedToString(1, year);
        assertEquals(expToString, yearEntity.toString());
    }

    private String expectedToString(int id, int year) {
        return "YearEntity(yearId=" + id + ", year=" + year + ", populations=null)";
    }
}

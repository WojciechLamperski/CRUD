package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class YearModelTest {

    private static final int yearId = 1;
    private static final int year = 2025;

    @Test
    public void testObjectGetters() {
        YearModel yearModel = new YearModel();
        yearModel.setYearId(yearId);
        yearModel.setYear(year);

        assertEquals(yearId, yearModel.getYearId());
        assertEquals(year, yearModel.getYear());
    }

    @Test
    public void testObjectIsReflexive() {
        YearModel yearModel = new YearModel();
        yearModel.setYearId(yearId);
        yearModel.setYear(year);

        assertEquals(yearModel, yearModel);
    }

}

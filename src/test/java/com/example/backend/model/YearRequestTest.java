package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YearRequestTest {

    private static final Integer yearId = 1;
    private static final int year = 2025;

    @Test
    public void testObjectGetters() {
        YearRequest yearRequest = new YearRequest();
        yearRequest.setYearId(yearId);
        yearRequest.setYear(year);

        assertEquals(yearId, yearRequest.getYearId());
        assertEquals(year, yearRequest.getYear());
    }

    @Test
    public void testObjectIsReflexive() {
        YearRequest yearRequest = new YearRequest();
        yearRequest.setYearId(yearId);
        yearRequest.setYear(year);

        assertTrue(yearRequest.equals(yearRequest));
    }
}
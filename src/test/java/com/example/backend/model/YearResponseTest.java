package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class YearResponseTest {

    private static final int pageNumber = 1;
    private static final int pageSize = 20;
    private static final long totalElements = 100L;
    private static final int totalPages = 5;
    private static final boolean last = true;
    private static final YearModel yearModel = new YearModel();

    static {
        yearModel.setYearId(1);
        yearModel.setYear(2025);
    }

    @Test
    public void testObjectGetters() {
        YearResponse yearResponse = new YearResponse(
                List.of(yearModel), pageNumber, pageSize, totalElements, totalPages, last);

        assertEquals(pageNumber, yearResponse.getPageNumber());
        assertEquals(pageSize, yearResponse.getPageSize());
        assertEquals(totalElements, yearResponse.getTotalElements());
        assertEquals(totalPages, yearResponse.getTotalPages());
        assertTrue(yearResponse.isLast());
        assertEquals(1, yearResponse.getContent().size());
        assertEquals(yearModel, yearResponse.getContent().get(0));
    }

    @Test
    public void testSetters() {
        YearResponse yearResponse = new YearResponse();
        yearResponse.setContent(List.of(yearModel));
        yearResponse.setPageNumber(pageNumber);
        yearResponse.setPageSize(pageSize);
        yearResponse.setTotalElements(totalElements);
        yearResponse.setTotalPages(totalPages);
        yearResponse.setLast(last);

        assertEquals(pageNumber, yearResponse.getPageNumber());
        assertEquals(pageSize, yearResponse.getPageSize());
        assertEquals(totalElements, yearResponse.getTotalElements());
        assertEquals(totalPages, yearResponse.getTotalPages());
        assertTrue(yearResponse.isLast());
        assertEquals(1, yearResponse.getContent().size());
        assertEquals(yearModel, yearResponse.getContent().get(0));
    }

    @Test
    public void testObjectIsReflexive() {
        YearResponse yearResponse = new YearResponse(
                List.of(yearModel), pageNumber, pageSize, totalElements, totalPages, last);

        assertTrue(yearResponse.equals(yearResponse));
    }
}


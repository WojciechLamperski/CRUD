package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class DistrictResponseTest {

    private static final int pageNumber = 1;
    private static final int pageSize = 20;
    private static final long totalElements = 100L;
    private static final int totalPages = 5;
    private static final boolean last = true;
    private static final DistrictModel districtModel = new DistrictModel();

    static {
        districtModel.setDistrictId(1);
        districtModel.setDistrict("Wroclaw");
        districtModel.setVoivodeship("Lower Silesia");
    }

    @Test
    public void testObjectGetters() {
        DistrictResponse districtResponse = new DistrictResponse(
                List.of(districtModel), pageNumber, pageSize, totalElements, totalPages, last);

        assertEquals(pageNumber, districtResponse.getPageNumber());
        assertEquals(pageSize, districtResponse.getPageSize());
        assertEquals(totalElements, districtResponse.getTotalElements());
        assertEquals(totalPages, districtResponse.getTotalPages());
        assertTrue(districtResponse.isLast());
        assertEquals(1, districtResponse.getContent().size());
        assertEquals(districtModel, districtResponse.getContent().get(0));
    }

    @Test
    public void testObjectIsReflexive() {
        DistrictResponse districtResponse = new DistrictResponse(
                List.of(districtModel), pageNumber, pageSize, totalElements, totalPages, last);

        assertTrue(districtResponse.equals(districtResponse));
    }

    @Test
    public void testSetters() {
        DistrictResponse districtResponse = new DistrictResponse();
        districtResponse.setContent(List.of(districtModel));
        districtResponse.setPageNumber(pageNumber);
        districtResponse.setPageSize(pageSize);
        districtResponse.setTotalElements(totalElements);
        districtResponse.setTotalPages(totalPages);
        districtResponse.setLast(last);

        assertEquals(pageNumber, districtResponse.getPageNumber());
        assertEquals(pageSize, districtResponse.getPageSize());
        assertEquals(totalElements, districtResponse.getTotalElements());
        assertEquals(totalPages, districtResponse.getTotalPages());
        assertTrue(districtResponse.isLast());
        assertEquals(1, districtResponse.getContent().size());
        assertEquals(districtModel, districtResponse.getContent().get(0));
    }
}

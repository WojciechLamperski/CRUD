package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class VoivodeshipResponseTest {

    private static final int pageNumber = 1;
    private static final int pageSize = 20;
    private static final long totalElements = 100L;
    private static final int totalPages = 5;
    private static final boolean last = true;
    private static final VoivodeshipModel voivodeshipModel = new VoivodeshipModel();

    static {
        voivodeshipModel.setVoivodeshipId(1);
        voivodeshipModel.setVoivodeship("Lower Silesia");
    }

    @Test
    public void testObjectGetters() {
        VoivodeshipResponse voivodeshipResponse = new VoivodeshipResponse(
                List.of(voivodeshipModel), pageNumber, pageSize, totalElements, totalPages, last);

        assertEquals(pageNumber, voivodeshipResponse.getPageNumber());
        assertEquals(pageSize, voivodeshipResponse.getPageSize());
        assertEquals(totalElements, voivodeshipResponse.getTotalElements());
        assertEquals(totalPages, voivodeshipResponse.getTotalPages());
        assertTrue(voivodeshipResponse.isLast());
        assertEquals(1, voivodeshipResponse.getContent().size());
        assertEquals(voivodeshipModel, voivodeshipResponse.getContent().get(0));
    }

    @Test
    public void testObjectIsReflexive() {
        VoivodeshipResponse voivodeshipResponse = new VoivodeshipResponse(
                List.of(voivodeshipModel), pageNumber, pageSize, totalElements, totalPages, last);

        assertTrue(voivodeshipResponse.equals(voivodeshipResponse));
    }

    @Test
    public void testSetters() {
        VoivodeshipResponse voivodeshipResponse = new VoivodeshipResponse();
        voivodeshipResponse.setContent(List.of(voivodeshipModel));
        voivodeshipResponse.setPageNumber(pageNumber);
        voivodeshipResponse.setPageSize(pageSize);
        voivodeshipResponse.setTotalElements(totalElements);
        voivodeshipResponse.setTotalPages(totalPages);
        voivodeshipResponse.setLast(last);

        assertEquals(pageNumber, voivodeshipResponse.getPageNumber());
        assertEquals(pageSize, voivodeshipResponse.getPageSize());
        assertEquals(totalElements, voivodeshipResponse.getTotalElements());
        assertEquals(totalPages, voivodeshipResponse.getTotalPages());
        assertTrue(voivodeshipResponse.isLast());
        assertEquals(1, voivodeshipResponse.getContent().size());
        assertEquals(voivodeshipModel, voivodeshipResponse.getContent().get(0));
    }
}

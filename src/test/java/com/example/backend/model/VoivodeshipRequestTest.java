package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VoivodeshipRequestTest {

    private static final int voivodeshipId = 1;
    private static final String voivodeship = "Mazowieckie";

    @Test
    public void testObjectGetters() {
        VoivodeshipRequest voivodeshipRequest = new VoivodeshipRequest();
        voivodeshipRequest.setVoivodeshipId(voivodeshipId);
        voivodeshipRequest.setVoivodeship(voivodeship);

        assertEquals(voivodeshipId, voivodeshipRequest.getVoivodeshipId());
        assertEquals(voivodeship, voivodeshipRequest.getVoivodeship());
    }

    @Test
    public void testObjectIsReflexive() {
        VoivodeshipRequest voivodeshipRequest = new VoivodeshipRequest();
        voivodeshipRequest.setVoivodeshipId(voivodeshipId);
        voivodeshipRequest.setVoivodeship(voivodeship);

        assertTrue(voivodeshipRequest.equals(voivodeshipRequest));
    }
}
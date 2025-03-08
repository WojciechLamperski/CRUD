package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VoivodeshipModelTest {

    private static final int voivodeshipId = 1;
    private static final String voivodeship = "Mazowieckie";

    @Test
    public void testObjectGetters() {
        VoivodeshipModel voivodeshipModel = new VoivodeshipModel();
        voivodeshipModel.setVoivodeshipId(voivodeshipId);
        voivodeshipModel.setVoivodeship(voivodeship);

        assertEquals(voivodeshipId, voivodeshipModel.getVoivodeshipId());
        assertEquals(voivodeship, voivodeshipModel.getVoivodeship());
    }

    @Test
    public void testObjectIsReflexive() {
        VoivodeshipModel voivodeshipModel = new VoivodeshipModel();
        voivodeshipModel.setVoivodeshipId(voivodeshipId);
        voivodeshipModel.setVoivodeship(voivodeship);

        assertTrue(voivodeshipModel.equals(voivodeshipModel));
    }

}

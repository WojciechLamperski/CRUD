package com.example.backend.entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VoivodeshipEntityTest {

    private static final String voivodeshipName = "Mazowieckie";

    @Test
    public void testObjectGetters_IdConstructor() {
        VoivodeshipEntity voivodeshipEntity = new VoivodeshipEntity();
        voivodeshipEntity.setVoivodeshipId(1);
        voivodeshipEntity.setVoivodeship(voivodeshipName);

        assertEquals(1, voivodeshipEntity.getVoivodeshipId());
        assertEquals(voivodeshipName, voivodeshipEntity.getVoivodeship());
    }

    @Test
    public void testObjectsAreEqual() {
        VoivodeshipEntity voivodeshipEntity1 = new VoivodeshipEntity();
        voivodeshipEntity1.setVoivodeship(voivodeshipName);

        VoivodeshipEntity voivodeshipEntity2 = new VoivodeshipEntity();
        voivodeshipEntity2.setVoivodeship(voivodeshipName);

        assertTrue(voivodeshipEntity1.equals(voivodeshipEntity2));
    }

    @Test
    public void testEqualObjectsHaveSameHashcode() {
        VoivodeshipEntity voivodeshipEntity1 = new VoivodeshipEntity();
        voivodeshipEntity1.setVoivodeship(voivodeshipName);

        VoivodeshipEntity voivodeshipEntity2 = new VoivodeshipEntity();
        voivodeshipEntity2.setVoivodeship(voivodeshipName);

        assertEquals(voivodeshipEntity1.hashCode(), voivodeshipEntity2.hashCode());
    }

    @Test
    public void testObjectIsReflexive() {
        VoivodeshipEntity voivodeshipEntity = new VoivodeshipEntity();
        voivodeshipEntity.setVoivodeship(voivodeshipName);

        assertTrue(voivodeshipEntity.equals(voivodeshipEntity));
    }

    @Test
    public void testToString() {
        VoivodeshipEntity voivodeshipEntity = new VoivodeshipEntity();
        voivodeshipEntity.setVoivodeshipId(1);
        voivodeshipEntity.setVoivodeship(voivodeshipName);

        String expToString = expectedToString(1, voivodeshipName);
        assertEquals(expToString, voivodeshipEntity.toString());
    }

    private String expectedToString(int id, String voivodeship) {
        return "VoivodeshipEntity(voivodeshipId=" + id + ", voivodeship=" + voivodeship + ", districts=null)";
    }
}

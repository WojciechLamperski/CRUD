package com.example.backend.POJO;

public class Voivodeship {
    private int voivodeshipId; // voivodeship_id
    private String voivodeship; // voivodeship

    public Voivodeship() {}

    public int getVoivodeshipId() {
        return voivodeshipId;
    }

    public void setVoivodeshipId(int voivodeshipId) {
        this.voivodeshipId = voivodeshipId;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    @Override
    public String toString() {
        return "Voivodeship{" +
                "voivodeshipId=" + voivodeshipId +
                ", voivodeship='" + voivodeship + '\'' +
                '}';
    }
}

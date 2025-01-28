package com.example.backend.POJO;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name="voivodeships")
public class Voivodeship {

    @Min(value = 1, message = "voivodeshipId must be greater than 0")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="voivodeship_id")
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

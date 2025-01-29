package com.example.backend.POJO;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name="voivodeships")
public class Voivodeship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="voivodeship_id")
    private int voivodeshipId; // voivodeship_id

    @NotEmpty()
    @Column(name="voivodeship")
    private String voivodeship; // voivodeship

    @OneToMany(mappedBy = "voivodeship")
    private List<District> districts;


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

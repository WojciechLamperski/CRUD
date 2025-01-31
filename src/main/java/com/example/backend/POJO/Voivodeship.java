package com.example.backend.POJO;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name="voivodeships")
public class Voivodeship {

    // TODO: test if you can create an id that equals to something like 100? Should that be the case?
    // TODO: test here and in all POJOs edge-cases in Postman.
    // TODO: see if you need getters and setters for all methods? and what about the toString?

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

package com.example.backend.POJO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;


@Entity
@Table(name="districts")
public class District {

    // TODO: test if you can create an id that equals to something like 100? Should that be the case?
    // TODO: test here and in all POJOs edge-cases in Postman.
    // TODO: see if you need getters and setters for all methods? and what about the toString?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="district_id")
    private int districtId;       // district_id

    @NotEmpty()
    @Column(name="district")
    private String district;      // district

    @NotNull
    @Min(value = 1, message = "voivodeshipId must be greater than 0")
    @Column(name="voivodeship_id")
    private int voivodeshipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voivodeship_id", referencedColumnName = "voivodeship_id", insertable = false, updatable = false)
    private Voivodeship voivodeship; // Voivodeship name stored in secondary table

    @OneToMany(mappedBy = "district")
    private List<Population> populations;

    public District() {}

    public void setVoivodeship(int voivodeshipId) {
        this.voivodeshipId = voivodeshipId;
    }

    public Voivodeship getVoivodeship() {
        return voivodeship;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getVoivodeshipId() {
        return voivodeshipId;
    }

    public void setVoivodeshipId(int voivodeshipId) {
        this.voivodeshipId = voivodeshipId;
    }

    @Override
    public String toString() {
        return "District{" +
                "districtId=" + districtId +
                ", district='" + district + '\'' +
                ", voivodeshipId=" + voivodeshipId +
                '}';
    }
}

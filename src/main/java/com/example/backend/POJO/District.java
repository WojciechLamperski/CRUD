package com.example.backend.POJO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name="districts")
public class District {

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

    public District() {}

    public District(String district, int voivodeshipId) {
        this.district = district;
        this.voivodeshipId = voivodeshipId;
    }

//    public Voivodeship getVoivodeship() {
//        return voivodeship;
//    }
//
//    public void setVoivodeship(Voivodeship voivodeship) {
//        this.voivodeship = voivodeship;
//    }

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

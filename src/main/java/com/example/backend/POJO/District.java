package com.example.backend.POJO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name="districts")
public class District {

    @Min(value = 1, message = "districtId must be greater than 0")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="district_id")
    private int districtId;       // district_id

    @NotEmpty
    @Column(name="district")
    private String district;      // district

    @NotNull
    @Column(name="voivodeship_id")
    private int voivodeshipId;    // voivodeship_id

    public District() {}

    public District(String district, int voivodeshipId) {
        this.district = district;
        this.voivodeshipId = voivodeshipId;
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

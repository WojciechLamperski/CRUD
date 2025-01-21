package com.example.backend.POJO;

public class District {
    private int districtId;       // district_id
    private String district;      // district
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

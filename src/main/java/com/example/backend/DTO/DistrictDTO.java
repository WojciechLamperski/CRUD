package com.example.backend.DTO;


public class DistrictDTO {
    private int districtId;       // district_id
    private String district;      // district
    private String voivodeship;    // voivodeship

    public DistrictDTO() {}

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

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }
}

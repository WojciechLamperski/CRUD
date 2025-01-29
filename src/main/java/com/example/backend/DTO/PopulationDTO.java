package com.example.backend.DTO;

import com.example.backend.POJO.Year;

public class PopulationDTO {
    private int populationId;  // population_id
    private Year year;        // year_id
    private DistrictDTO district;    // district_id
    private int men;           // number of men
    private int women;         // number of women

    public PopulationDTO(int populationId, Year year, int men, DistrictDTO district, int women) {
        this.populationId = populationId;
        this.year = year;
        this.men = men;
        this.district = district;
        this.women = women;
    }

    public PopulationDTO(){

    }

    public int getPopulationId() {
        return populationId;
    }

    public void setPopulationId(int populationId) {
        this.populationId = populationId;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    public int getMen() {
        return men;
    }

    public void setMen(int men) {
        this.men = men;
    }

    public int getWomen() {
        return women;
    }

    public void setWomen(int women) {
        this.women = women;
    }
}

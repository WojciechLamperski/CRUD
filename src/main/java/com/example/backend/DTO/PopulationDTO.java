package com.example.backend.DTO;


public class PopulationDTO {
    private int populationId;  // population_id
    private int year;        // year_id
    private DistrictDTO district;    // district_id
    private int men;           // number of men
    private int women;         // number of women

    public PopulationDTO(int populationId, int year, DistrictDTO district, int men, int women) {
        this.populationId = populationId;
        this.year = year;
        this.district = district;
        this.men = men;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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

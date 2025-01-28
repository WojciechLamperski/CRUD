package com.example.backend.POJO;

public class Population {
    private int populationId;  // population_id
    private int yearId;        // year_id
    private int districtId;    // district_id
    private int men;           // men (number of men)
    private int women;         // women (number of women)

    public Population() {}

    public Population(int yearId, int districtId, int men, int women) {
        this.yearId = yearId;
        this.districtId = districtId;
        this.men = men;
        this.women = women;
    }

    public int getPopulationId() {
        return populationId;
    }

    public void setPopulationId(int populationId) {
        this.populationId = populationId;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
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

    @Override
    public String toString() {
        return "Population{" +
                "populationId=" + populationId +
                ", yearId=" + yearId +
                ", districtId=" + districtId +
                ", men=" + men +
                ", women=" + women +
                '}';
    }
}
package com.example.backend.POJO;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="population")
public class Population {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="population_id")
    private int populationId;  // population_id

    @NotNull
    @Min(value = 1, message = "yearId must be greater than 0")
    private int yearId;        // year_id

    @NotNull
    @Min(value = 1, message = "districtId must be greater than 0")
    private int districtId;    // district_id

    @NotNull
    @Min(value = 0, message = "number of men can't be negative")
    private int men;           // men (number of men)

    @NotNull
    @Min(value = 0, message = "number of women can't be negative")
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
package com.example.backend.POJO;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="population")
public class Population {

    // TODO: test if you can create an id that equals to something like 100? Should that be the case?
    // TODO: test here and in all POJOs edge-cases in Postman.
    // TODO: see if you need getters and setters for all methods? and what about the toString?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="population_id")
    private int populationId;  // population_id

    @NotNull
    @Min(value = 1, message = "yearId must be greater than 0")
    @Column(name="year_id")
    private int yearId;        // year_id

    @NotNull
    @Min(value = 1, message = "districtId must be greater than 0")
    @Column(name="district_id")
    private int districtId;    // district_id

    @NotNull
    @Min(value = 0, message = "number of men can't be negative")
    @Column(name="men")
    private int men;           // men (number of men)

    @NotNull
    @Min(value = 0, message = "number of women can't be negative")
    @Column(name="women")
    private int women;         // women (number of women)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", referencedColumnName = "district_id", insertable = false, updatable = false)
    private District district; // Voivodeship name stored in secondary table

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_id", referencedColumnName = "year_id", insertable = false, updatable = false)
    private Year year; // Voivodeship name stored in secondary table

    public Population() {}

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
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
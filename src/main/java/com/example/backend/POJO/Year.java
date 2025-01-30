package com.example.backend.POJO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="years")
public class Year {

    // TODO: test if you can create an id that equals to something like 100? Should that be the case?
    // TODO: test here and in all POJOs edge-cases in Postman.
    // TODO: see if you need getters and setters for all methods? and what about the toString?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="year_id")
    private int yearId;

    @NotNull
    @Min(value = 1, message = "year must be greater than 0")
    @Column(name="year")
    private int year;

    @OneToMany(mappedBy = "year")
    private List<Population> populations;

    public Year() {
    }

    public Year(int year) {
        this.year = year;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Years{" +
                "yearId=" + yearId +
                ", year='" + year + '\'' +
                '}';
    }
}

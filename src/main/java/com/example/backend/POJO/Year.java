package com.example.backend.POJO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="years")
public class Year {

    @Min(value = 1, message = "yearId must be greater than 0")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="year_id")
    private int yearId;

    @NotNull
    @Min(value = 1, message = "year must be greater than 0")
    @Column(name="year")
    private int year;

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

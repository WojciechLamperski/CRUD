package com.example.backend.POJO;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="year")
@JsonRootName("year")
public class Year {

    @NotNull
    @Min(value = 1, message = "Year must be a positive number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="year_id")
    private int yearId;

    @NotNull
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

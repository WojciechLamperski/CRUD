package com.example.backend.POJO;

public class Year {

    private int yearId;
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

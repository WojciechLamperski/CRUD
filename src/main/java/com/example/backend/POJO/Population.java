package com.example.backend.POJO;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

}
package com.example.backend.POJO;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="populations")
public class Population {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="population_id")
    private int populationId;  // population_id

    @Min(value = 1, message = "yearId must be greater than 0")
    @Column(name="year_id")
    private Integer yearId;        // year_id

    @Min(value = 1, message = "districtId must be greater than 0")
    @Column(name="district_id")
    private Integer districtId;    // district_id

    @NotNull(message = "men can't be null")
    @Min(value = 0, message = "number of men can't be negative")
    @Column(name="men")
    private int men;           // men (number of men)

    @NotNull(message = "women can't be null")
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
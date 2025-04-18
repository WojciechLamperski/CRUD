package com.example.backend.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="populations")
public class PopulationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="population_id")
    private int populationId;

    @Min(value = 1, message = "yearId must be greater than 0")
    @Column(name="year_id")
    private Integer yearId;

    @Min(value = 1, message = "districtId must be greater than 0")
    @Column(name="district_id")
    private Integer districtId;

    @Min(value = 0, message = "number of men can't be negative")
    @NotNull(message = "men can't be null")
    @Column(name="men")
    private Integer men;           // number of men

    @Min(value = 0, message = "number of women can't be negative")
    @NotNull(message = "women can't be null")
    @Column(name="women")
    private Integer women;         // number of women

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", referencedColumnName = "district_id", insertable = false, updatable = false)
    private DistrictEntity district; // District entity stored in secondary table

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "year_id", referencedColumnName = "year_id", insertable = false, updatable = false)
    private YearEntity year; // Year entity stored in secondary table

}
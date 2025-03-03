package com.example.backend.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PopulationModel {
    private int populationId;  // population_id
    private Integer year;        // year_id
    private DistrictModel district;    // district_id
    private int men;           // number of men
    private int women;         // number of women
}

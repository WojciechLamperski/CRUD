package com.example.backend.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PopulationDTO {
    private int populationId;  // population_id
    private int year;        // year_id
    private DistrictDTO district;    // district_id
    private int men;           // number of men
    private int women;         // number of women
}

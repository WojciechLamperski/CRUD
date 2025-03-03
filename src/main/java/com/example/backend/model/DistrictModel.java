package com.example.backend.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DistrictModel {
    private int districtId;       // district_id
    private String district;      // district
    private String voivodeship;    // voivodeship
}

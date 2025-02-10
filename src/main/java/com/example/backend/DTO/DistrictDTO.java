package com.example.backend.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DistrictDTO {
    private int districtId;       // district_id
    private String district;      // district
    private String voivodeship;    // voivodeship
}

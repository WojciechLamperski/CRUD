package com.example.backend.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TempModel {
    // Rok => Year
    @JsonProperty("Rok")
    private Integer year;

    // Powiat => District
    @JsonProperty("Powiat")
    private String district;

    // Województwo => voivodeship
    @JsonProperty("Województwo")
    private String voivodeship;

    // Kobiety => women
    @JsonProperty("Kobiety")
    private Integer women;

    // Mężczyźni => men
    @JsonProperty("Mężczyźni")
    private Integer men;
}

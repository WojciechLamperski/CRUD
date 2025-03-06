package com.example.backend.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TempModel {
//    Rok => Year
//    Województwo => voivodeship =>
//    Powiat => District
//    Kobiety => women
//    Mężczyźni => men

    @JsonProperty("Rok")
    private Integer year;
    @JsonProperty("Powiat")
    private String district;
    @JsonProperty("Województwo")
    private String voivodeship;
    @JsonProperty("Kobiety")
    private Integer women;
    @JsonProperty("Mężczyźni")
    private Integer men;
}

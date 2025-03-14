package com.example.backend.model;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopulationRequest {

    private Integer populationId;
    private Integer yearId;
    private Integer districtId;

    @Min(value = 1, message = "number of men must be greater than 0")
    private int men;

    @Min(value = 1, message = "number of women must be greater than 0")
    private int women;
}

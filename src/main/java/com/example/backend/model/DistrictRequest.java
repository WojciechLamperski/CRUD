package com.example.backend.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistrictRequest {
    private Integer districtId;
    private Integer voivodeshipId;
    @NotEmpty(message = "district can't be null")
    @NotNull(message = "district can't be null")
    private String district;
}

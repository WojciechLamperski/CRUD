package com.example.backend.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YearRequest {

    private Integer yearId;

    @NotNull(message = "year can't be null")
    @Min(value = 1, message = "year must be greater than 0")
    private int year;
}

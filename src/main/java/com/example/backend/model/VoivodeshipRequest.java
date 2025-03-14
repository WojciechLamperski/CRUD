package com.example.backend.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoivodeshipRequest {
    private int voivodeshipId;

    @NotEmpty(message = "voivodeship can't be null")
    @NotNull(message = "voivodeship can't be null")
    private String voivodeship;
}

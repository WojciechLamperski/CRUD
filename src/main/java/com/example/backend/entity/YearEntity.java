package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="years")
public class YearEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="year_id")
    private int yearId;

    @NotNull(message = "year can't be null")
    @Min(value = 1, message = "year must be greater than 0")
    @Column(name="`year`")
    private int year;

    @JsonIgnore
    @OneToMany(mappedBy = "year")
    private List<PopulationEntity> populations;

}

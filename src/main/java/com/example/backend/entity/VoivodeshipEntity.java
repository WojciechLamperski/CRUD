package com.example.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="voivodeships")
public class VoivodeshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="voivodeship_id")
    private int voivodeshipId; // voivodeship_id

    @NotEmpty(message = "voivodeship can't be empty")
    @Column(name="voivodeship")
    private String voivodeship; // voivodeship

    @JsonIgnore
    @OneToMany(mappedBy = "voivodeship")
    private List<DistrictEntity> districts;

}

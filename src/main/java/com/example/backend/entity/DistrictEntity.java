package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name="districts")
public class DistrictEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="district_id")
    private int districtId;       // district_id

    @NotEmpty(message = "district can't be empty")
    @Column(name="district")
    private String district;      // district

    @Min(value = 1, message = "voivodeshipId must be greater than 0")
    @Column(name="voivodeship_id")
    private Integer voivodeshipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voivodeship_id", referencedColumnName = "voivodeship_id", insertable = false, updatable = false)
    private VoivodeshipEntity voivodeship; // Voivodeship name stored in secondary table

    @JsonIgnore
    @OneToMany(mappedBy = "district")
    private List<PopulationEntity> populations;
}


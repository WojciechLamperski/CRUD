package com.example.backend.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name="districts")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="district_id")
    private int districtId;       // district_id

    @NotEmpty(message = "district can't be empty")
    @Column(name="district")
    private String district;      // district

    @NotNull(message = "voivodeshipId can't be null")
    @Min(value = 1, message = "voivodeshipId must be greater than 0")
    @Column(name="voivodeship_id")
    private int voivodeshipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voivodeship_id", referencedColumnName = "voivodeship_id", insertable = false, updatable = false)
    private Voivodeship voivodeship; // Voivodeship name stored in secondary table

    @JsonIgnore
    @OneToMany(mappedBy = "district")
    private List<Population> populations;
}


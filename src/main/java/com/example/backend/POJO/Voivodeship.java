package com.example.backend.POJO;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="voivodeships")
public class Voivodeship {

    // TODO: test if you can create an id that equals to something like 100? Should that be the case?
    // TODO: test here and in all POJOs edge-cases in Postman.
    // TODO: see if you need getters and setters for all methods? and what about the toString?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="voivodeship_id")
    private int voivodeshipId; // voivodeship_id

    @NotEmpty(message = "voivodeship can't be empty")
    @Column(name="voivodeship")
    private String voivodeship; // voivodeship

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "voivodeship")
    private List<District> districts;

}

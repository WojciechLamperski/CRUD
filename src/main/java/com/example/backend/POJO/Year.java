package com.example.backend.POJO;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="years")
public class Year {

    // TODO: test if you can create an id that equals to something like 100? Should that be the case?
    // TODO: test here and in all POJOs edge-cases in Postman.
    // TODO: see if you need getters and setters for all methods? and what about the toString?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="year_id")
    private int yearId;

    @NotNull(message = "year can't be null")
    @Min(value = 1, message = "year must be greater than 0")
    @Column(name="year")
    private int year;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "year")
    private List<Population> populations;

}

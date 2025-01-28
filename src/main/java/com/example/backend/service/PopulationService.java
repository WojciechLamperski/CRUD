package com.example.backend.service;

import com.example.backend.POJO.Population;

import java.util.List;

public interface PopulationService{
    String save(Population population);
    Population findById(int id);
    List<Population> findAll();
    String delete(int id);
}

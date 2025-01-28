package com.example.backend.DAO;

import com.example.backend.POJO.Population;

import java.util.List;

public interface PopulationDAO {
    String save(Population population);
    Population findById(int id);
    List<Population> findAll();
    String delete(int id);
}

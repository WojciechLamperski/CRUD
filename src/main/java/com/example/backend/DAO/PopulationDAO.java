package com.example.backend.DAO;

import com.example.backend.POJO.Population;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PopulationDAO {
    String save(Population population);
    Population findById(int id);
    List<Population> findAll();
    Page<Population> findAll(Pageable pageable);
    String delete(int id);
}

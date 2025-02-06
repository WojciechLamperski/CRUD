package com.example.backend.DAO;

import com.example.backend.POJO.Population;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import java.util.List;

public interface PopulationDAO {
    String save(Population population);
    Population findById(int id);
    Page<Population> findAll(Pageable pageable, Sort sort);
    String delete(int id);
}

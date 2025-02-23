package com.example.backend.DAO;

import com.example.backend.POJO.Population;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public interface PopulationDAO {
    String save(Population population);
    Population findById(int id);
    Page<Population> findAll(Pageable pageable, Sort sort);
    Page<Population> findAllInVoivodeship(Pageable pageable, Sort sort, int voivodeshipId);
    Page<Population> findAllInDistrict(Pageable pageable, Sort sort, int districtId);
    Page<Population> findAllInYear(Pageable pageable, Sort sort, int yearId);
    String delete(int id);
}

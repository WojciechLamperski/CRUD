package com.example.backend.repository;

import com.example.backend.entity.PopulationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public interface PopulationRepository {
    String save(PopulationEntity population);
    PopulationEntity findById(int id);
    Page<PopulationEntity> findAll(Pageable pageable, Sort sort);
    Page<PopulationEntity> findAllInVoivodeship(Pageable pageable, Sort sort, int voivodeshipId);
    Page<PopulationEntity> findAllInDistrict(Pageable pageable, Sort sort, int districtId);
    Page<PopulationEntity> findAllInYear(Pageable pageable, Sort sort, int yearId);
    String delete(int id);
}

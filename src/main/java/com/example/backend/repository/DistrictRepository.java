package com.example.backend.repository;

import com.example.backend.entity.DistrictEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public interface DistrictRepository {
    String save(DistrictEntity district);
    DistrictEntity findById(int id);
    Page<DistrictEntity> findAll(Pageable pageable, Sort sort);
    Page<DistrictEntity> findAllInVoivodeship(Pageable pageable, Sort sort, int voivodeshipId);
    String delete(int id);
}

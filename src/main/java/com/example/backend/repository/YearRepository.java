package com.example.backend.repository;

import com.example.backend.entity.YearEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;


public interface YearRepository {
    YearEntity save(YearEntity year);
    YearEntity findById(int id);
    Page<YearEntity> findAll(Pageable pageable, Sort sort);
    String delete(int id);
}

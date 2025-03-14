package com.example.backend.repository;

import com.example.backend.entity.YearEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface YearCustomRepository {
    Page<YearEntity> findAll(Pageable pageable, Sort sort);

}

package com.example.backend.repository;

import com.example.backend.entity.VoivodeshipEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public interface VoivodeshipRepository {
    VoivodeshipEntity save(VoivodeshipEntity voivodeship);
    VoivodeshipEntity findById(int id);
    Page<VoivodeshipEntity> findAll(Pageable pageable, Sort sort);
    String delete(int id);
}

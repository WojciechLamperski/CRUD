package com.example.backend.DAO;

import com.example.backend.POJO.Voivodeship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface VoivodeshipDAO {
    String save(Voivodeship voivodeship);
    Voivodeship findById(int id);
    Page<Voivodeship> findAll(Pageable pageable, Sort sort);
    String delete(int id);
}

package com.example.backend.DAO;

import com.example.backend.POJO.Voivodeship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoivodeshipDAO {
    String save(Voivodeship voivodeship);
    Voivodeship findById(int id);
    List<Voivodeship> findAll();
    Page<Voivodeship> findAll(Pageable pageable);
    String delete(int id);
}

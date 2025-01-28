package com.example.backend.DAO;

import com.example.backend.POJO.Voivodeship;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface VoivodeshipDAO {
    String save(Voivodeship voivodeship);
    Voivodeship findById(int id);
    List<Voivodeship> findAll();
    String delete(int id);
}

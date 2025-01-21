package com.example.backend.DAO;

import com.example.backend.POJO.Voivodeship;

import java.util.List;

public interface VoivodeshipDAO {
    int save(Voivodeship voivodeship);
    Voivodeship findById(int id);
    List<Voivodeship> findAll();
    int update(Voivodeship voivodeship);
    int delete(int id);
}

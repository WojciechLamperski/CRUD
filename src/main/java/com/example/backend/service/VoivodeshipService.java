package com.example.backend.service;

import com.example.backend.POJO.Voivodeship;

import java.util.List;

public interface VoivodeshipService {
    String save(Voivodeship voivodeship);
    Voivodeship findById(int id);
    List<Voivodeship> findAll();
    String delete(int id);
}

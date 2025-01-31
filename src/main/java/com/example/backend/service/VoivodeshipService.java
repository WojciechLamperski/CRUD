package com.example.backend.service;

import com.example.backend.DTO.VoivodeshipResponse;
import com.example.backend.POJO.Voivodeship;


public interface VoivodeshipService {
    String save(Voivodeship voivodeship);
    Voivodeship findById(int id);
    VoivodeshipResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    String delete(int id);
}

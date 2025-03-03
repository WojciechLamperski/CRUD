package com.example.backend.service;

import com.example.backend.model.VoivodeshipResponse;
import com.example.backend.entity.VoivodeshipEntity;


public interface VoivodeshipService {
    String save(VoivodeshipEntity voivodeship);
    VoivodeshipEntity findById(int id);
    VoivodeshipResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    String delete(int id);
}

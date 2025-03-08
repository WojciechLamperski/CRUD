package com.example.backend.service;

import com.example.backend.model.VoivodeshipModel;
import com.example.backend.model.VoivodeshipResponse;
import com.example.backend.entity.VoivodeshipEntity;


public interface VoivodeshipService {
    VoivodeshipModel save(VoivodeshipEntity voivodeship);
    VoivodeshipModel findById(int id);
    VoivodeshipResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    String delete(int id);
}

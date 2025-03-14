package com.example.backend.service;

import com.example.backend.model.VoivodeshipModel;
import com.example.backend.model.VoivodeshipRequest;
import com.example.backend.model.VoivodeshipResponse;


public interface VoivodeshipService {
    VoivodeshipModel save(VoivodeshipRequest voivodeship);
    VoivodeshipModel findById(int id);
    VoivodeshipResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    String delete(int id);
}

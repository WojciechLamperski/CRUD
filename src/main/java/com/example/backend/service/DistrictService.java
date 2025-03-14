package com.example.backend.service;

import com.example.backend.model.DistrictModel;
import com.example.backend.model.DistrictRequest;
import com.example.backend.model.DistrictResponse;
import com.example.backend.entity.DistrictEntity;


public interface DistrictService {
    DistrictModel save(DistrictRequest district);
    DistrictModel findById(int id);
    DistrictResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    DistrictResponse findAllInVoivodeship(int voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    void delete(int id);
}

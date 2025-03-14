package com.example.backend.service;

import com.example.backend.model.PopulationModel;
import com.example.backend.model.PopulationRequest;
import com.example.backend.model.PopulationResponse;


public interface PopulationService{
    PopulationModel save(PopulationRequest population);
    PopulationModel findById(int id);
    PopulationResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    PopulationResponse findAllInDistrict(int districtId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    PopulationResponse findAllInYear(int yearId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    PopulationResponse findAllInVoivodeship(int voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    String delete(int id);
}

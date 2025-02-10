package com.example.backend.service;

import com.example.backend.DTO.PopulationDTO;
import com.example.backend.DTO.PopulationResponse;
import com.example.backend.POJO.Population;


public interface PopulationService{
    String save(Population population);
    PopulationDTO findById(int id);
    PopulationResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    PopulationResponse findAllInDistrict(int districtId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    PopulationResponse findAllInYear(int yearId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    PopulationResponse findAllInVoivodeship(int voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    String delete(int id);
}

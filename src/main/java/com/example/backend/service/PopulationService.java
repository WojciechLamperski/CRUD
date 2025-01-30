package com.example.backend.service;

import com.example.backend.DTO.PopulationDTO;
import com.example.backend.DTO.PopulationResponse;
import com.example.backend.POJO.Population;

import java.util.List;

public interface PopulationService{
    String save(Population population);
    PopulationDTO findById(int id);
    PopulationResponse findAll(int pageNumber, int pageSize);
    String delete(int id);
    PopulationDTO convertToDTO(Population population);
}

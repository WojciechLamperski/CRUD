package com.example.backend.service;

import com.example.backend.DTO.DistrictDTO;
import com.example.backend.POJO.District;

import java.util.List;

public interface DistrictService {
    String save(District district);
    DistrictDTO findById(int id);
    List<DistrictDTO> findAll();
    String delete(int id);
    DistrictDTO convertToDTO(District district);
}

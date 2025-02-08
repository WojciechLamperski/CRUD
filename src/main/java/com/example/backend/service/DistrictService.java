package com.example.backend.service;

import com.example.backend.DTO.DistrictDTO;
import com.example.backend.DTO.DistrictResponse;
import com.example.backend.POJO.District;


public interface DistrictService {
    String save(District district);
    DistrictDTO findById(int id);
    DistrictResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    DistrictResponse findAllInVoivodeship(int voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection);
    String delete(int id);
    DistrictDTO convertToDTO(District district);
}

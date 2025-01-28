package com.example.backend.service;

import com.example.backend.POJO.District;

import java.util.List;

public interface DistrictService {
    String save(District district);
    District findById(int id);
    List<District> findAll();
    String delete(int id);
}

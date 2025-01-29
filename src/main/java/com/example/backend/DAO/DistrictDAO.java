package com.example.backend.DAO;

import com.example.backend.POJO.District;

import java.util.List;

public interface DistrictDAO{
    String save(District district);
    District findById(int id);
    List<District> findAll();
    String delete(int id);
}

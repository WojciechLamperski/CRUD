package com.example.backend.DAO;

import com.example.backend.POJO.District;

import java.util.List;

public interface DistrictDAO {
    int save(District district);
    District findById(int id);
    List<District> findAll();
    int delete(int id);
}

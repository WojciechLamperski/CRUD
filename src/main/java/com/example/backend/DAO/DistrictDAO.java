package com.example.backend.DAO;

import com.example.backend.POJO.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DistrictDAO{
    String save(District district);
    District findById(int id);
    List<District> findAll();
    Page<District> findAll(Pageable pageable);
    String delete(int id);
}

package com.example.backend.DAO;

import com.example.backend.POJO.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public interface DistrictDAO{
    String save(District district);
    District findById(int id);
    Page<District> findAll(Pageable pageable, Sort sort);
    Page<District> findAllInVoivodeship(Pageable pageable, Sort sort, int voivodeshipId);
    String delete(int id);
}

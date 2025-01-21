package com.example.backend.DAO;

import com.example.backend.POJO.Year;

import java.util.List;
import java.util.Optional;

public interface YearDAO {
    int save(Year year);
    Optional<Year> findById(int id);
    List<Year> findAll();
    int update(Year year);
    int delete(int id);
}

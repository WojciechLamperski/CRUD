package com.example.backend.DAO;

import com.example.backend.POJO.Year;

import java.util.List;


public interface YearDAO {
    int save(Year year);
    Year findById(int id);
    List<Year> findAll();
    int update(Year year);
    int delete(int id);
}

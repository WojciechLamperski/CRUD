package com.example.backend.DAO;

import com.example.backend.POJO.Year;

import java.util.List;


public interface YearDAO {
    String save(Year year);
    Year findById(int id);
    List<Year> findAll();
    String delete(int id);
}

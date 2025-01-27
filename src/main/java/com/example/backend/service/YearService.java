package com.example.backend.service;

import com.example.backend.POJO.Year;

import java.util.List;

public interface YearService {
    int save(Year year);
    Year findById(int id);
    List<Year> findAll();
    int update(Year year);
    int delete(int id);
}


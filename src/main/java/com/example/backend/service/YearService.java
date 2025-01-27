package com.example.backend.service;

import com.example.backend.POJO.Year;

import java.util.List;

public interface YearService {
    String save(Year year);
    Year findById(int id);
    List<Year> findAll();
    String delete(int id);
}


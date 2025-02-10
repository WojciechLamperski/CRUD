package com.example.backend.service;

import com.example.backend.DTO.YearResponse;
import com.example.backend.POJO.Year;


public interface YearService {
    String save(Year year);
    Year findById(int id);
    YearResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    String delete(int id);
}


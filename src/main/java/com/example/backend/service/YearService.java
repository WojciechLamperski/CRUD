package com.example.backend.service;

import com.example.backend.model.YearModel;
import com.example.backend.model.YearResponse;
import com.example.backend.entity.YearEntity;


public interface YearService {
    YearModel save(YearEntity year);
    YearModel findById(int id);
    YearResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    String delete(int id);
}


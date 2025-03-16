package com.example.backend.service;

import com.example.backend.model.YearModel;
import com.example.backend.model.YearRequest;
import com.example.backend.model.YearResponse;


public interface YearService {
    YearModel save(YearRequest year);
    YearModel findById(int id);
    YearResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection);
    void delete(int id);
}


package com.example.backend.DTO;

import com.example.backend.POJO.Voivodeship;
import com.example.backend.POJO.Year;

import java.util.List;

public class YearResponse {
    private List<Year> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}

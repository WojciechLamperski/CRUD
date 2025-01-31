package com.example.backend.DTO;

import com.example.backend.POJO.Voivodeship;

import java.util.List;

public class VoivodeshipResponse {
    private List<Voivodeship> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}

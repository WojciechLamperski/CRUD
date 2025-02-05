package com.example.backend.DTO;

import com.example.backend.POJO.Voivodeship;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VoivodeshipResponse {
    private List<Voivodeship> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}

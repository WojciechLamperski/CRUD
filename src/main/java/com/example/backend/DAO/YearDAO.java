package com.example.backend.DAO;

import com.example.backend.POJO.Year;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface YearDAO {
    String save(Year year);
    Year findById(int id);
    List<Year> findAll();
    Page<Year> findAll(Pageable pageable, Sort sort);
    String delete(int id);
}

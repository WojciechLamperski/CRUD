package com.example.backend.service;

import com.example.backend.DAO.YearDAO;
import com.example.backend.DTO.YearResponse;
import com.example.backend.POJO.Voivodeship;
import com.example.backend.POJO.Year;
import com.example.backend.POJO.Year;
import com.example.backend.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class YearServiceImpl implements YearService {

    private YearDAO yearDAO;

    public YearServiceImpl(YearDAO theYearDAO) {
        yearDAO = theYearDAO;
    }

    @Override
    @Transactional
    public String save(Year year) {
        // try {
            return yearDAO.save(year);
        // } catch (DataAccessException e) {
            // throw new DatabaseException("Error saving Year entity", e);
        // }
    }

    @Override
    public Year findById(int id) {
        try {
            return yearDAO.findById(id);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Year with id " + id + " not found");
        }
    }

    @Override
    public YearResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // try {
        Page<Year> year = yearDAO.findAll(pageable, sort);
        List<Year> content = year.stream().collect(Collectors.toList());

        YearResponse voivodeshipResponse = new YearResponse();
        voivodeshipResponse.setContent(content);
        voivodeshipResponse.setPageNumber(year.getNumber());
        voivodeshipResponse.setPageSize(year.getSize());
        voivodeshipResponse.setTotalElements(year.getTotalElements());
        voivodeshipResponse.setTotalPages(year.getTotalPages());
        voivodeshipResponse.setLast(year.isLast());
        // } catch (DataAccessException e) {
            // throw new DatabaseException("Error retrieving all Year entities", e);
        // }
        return voivodeshipResponse;
    }

    @Override
    @Transactional
    public String delete(int id) {
        try {
            return yearDAO.delete(id);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Year with id " + id + " not found");
        }
    }
}

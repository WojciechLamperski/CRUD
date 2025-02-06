package com.example.backend.service;

import com.example.backend.DAO.YearDAO;
import com.example.backend.DTO.YearResponse;
import com.example.backend.POJO.District;
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

        if(year.getYearId() != 0 && yearDAO.findById(year.getYearId()) == null){
            throw new EntityNotFoundException("Year which you're trying to update was not found");
        }
        return yearDAO.save(year);
    }

    @Override
    public Year findById(int id) {

        Year year = yearDAO.findById(id);
        if (year == null) {
            throw new EntityNotFoundException("Year not found");
        }
        return yearDAO.findById(id);
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

        Year year = yearDAO.findById(id);
        if (year == null) {
            throw new EntityNotFoundException("Year not found");
        }
        return yearDAO.delete(id);
    }
}

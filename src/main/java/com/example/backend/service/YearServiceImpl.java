package com.example.backend.service;

import com.example.backend.entity.YearEntity;
import com.example.backend.model.YearModel;
import com.example.backend.model.YearResponse;
import com.example.backend.repository.YearRepository;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.exception.InvalidSortFieldException;
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

    private final YearRepository yearRepository;

    public YearServiceImpl(YearRepository theYearRepository) {
        yearRepository = theYearRepository;
    }

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("yearId", "year");

    @Override
    @Transactional
    public String save(YearEntity year) {

        if(year.getYearId() != 0 && yearRepository.findById(year.getYearId()) == null){
            throw new EntityNotFoundException("Year which you're trying to update was not found");
        }
        return yearRepository.save(year);
    }

    @Override
    public YearEntity findById(int id) {

        YearEntity year = yearRepository.findById(id);
        if (year == null) {
            throw new EntityNotFoundException("Year not found");
        }
        return yearRepository.findById(id);
    }

    @Override
    public YearResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);


        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<YearEntity> year = yearRepository.findAll(pageable, sort);
        List<YearEntity> content = year.stream().collect(Collectors.toList());

        return convertToResponse(null, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    @Transactional
    public String delete(int id) {

        YearEntity year = yearRepository.findById(id);
        if (year == null) {
            throw new EntityNotFoundException("Year not found");
        }
        return yearRepository.delete(id);
    }


    public YearModel convertToModel(YearEntity district) {

        YearModel voivodeshipModel = new YearModel();
        voivodeshipModel.setYearId(district.getYearId());
        voivodeshipModel.setYear(district.getYear());

        return voivodeshipModel;
    }

    public YearResponse convertToResponse(Integer voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<YearEntity> voivodeships;
        voivodeships = yearRepository.findAll(pageable, sort);

        List<YearModel> content = voivodeships.stream().map(this::convertToModel).collect(Collectors.toList());

        return new YearResponse(
                content, voivodeships.getNumber(), voivodeships.getSize(), voivodeships.getTotalElements(), voivodeships.getTotalPages(), voivodeships.isLast()
        );
    }
}

package com.example.backend.service;

import com.example.backend.entity.YearEntity;
import com.example.backend.model.YearModel;
import com.example.backend.model.YearResponse;
import com.example.backend.repository.YearRepository;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.exception.InvalidSortFieldException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(YearServiceImpl.class);

    private final YearRepository yearRepository;

    public YearServiceImpl(YearRepository theYearRepository) {
        yearRepository = theYearRepository;
    }

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("yearId", "year");

    @Override
    @Transactional
    public YearEntity save(YearEntity year) {
        logger.info("service received request to save / update year {}", year);
        if(year.getYearId() != 0 && yearRepository.findById(year.getYearId()) == null){
            logger.info("can't update because year with this id doesn't exists");
            throw new EntityNotFoundException("Year which you're trying to update was not found");
        }
        return yearRepository.save(year);
    }

    @Override
    public YearEntity findById(int id) {
        logger.info("service received request to find year by Id");
        YearEntity year = yearRepository.findById(id);
        if (year == null) {
            logger.info("year not found");
            throw new EntityNotFoundException("Year not found");
        }
        return yearRepository.findById(id);
    }

    @Override
    public YearResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        logger.info("service received request to find all years");
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            logger.info("found invalid sort field in years service");
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

        return convertToResponse(pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    @Transactional
    public String delete(int id) {
        logger.info("service received request to delete year");
        YearEntity year = yearRepository.findById(id);
        if (year == null) {
            logger.info("year not found in service");
            throw new EntityNotFoundException("Year not found");
        }
        return yearRepository.delete(id);
    }


    public YearModel convertToModel(YearEntity district) {
        logger.info("converting year to model in service");
        YearModel voivodeshipModel = new YearModel();
        voivodeshipModel.setYearId(district.getYearId());
        voivodeshipModel.setYear(district.getYear());

        return voivodeshipModel;
    }

    public YearResponse convertToResponse(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        logger.info("finding all years and converting them to response in service");
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

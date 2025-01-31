package com.example.backend.service;

import com.example.backend.DAO.PopulationDAO;
import com.example.backend.DTO.PopulationResponse;
import com.example.backend.DTO.PopulationDTO;
import com.example.backend.POJO.Population;
import com.example.backend.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PopulationServiceImpl implements PopulationService {

    private PopulationDAO populationDAO;

    public PopulationServiceImpl(PopulationDAO thePopulationDAO) {
        populationDAO = thePopulationDAO;
    }

    @Override
    @Transactional
    public String save(Population population) {
//        try {
        return populationDAO.save(population);
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Error saving Population entity", e);
//        }
    }

    @Override
    public PopulationDTO findById(int id) {
//        try {
            Population population = populationDAO.findById(id);
            return (population != null) ? convertToDTO(population) : null;
//        } catch (RuntimeException e) {
//            throw new EntityNotFoundException("Population with id " + id + " not found");
//        }
    }

    @Override
    public PopulationResponse findAll(int pageNumber, int pageSize) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // try {
        Page<Population> populations = populationDAO.findAll(pageable);
        List<PopulationDTO> content = populations.stream().map(this::convertToDTO).collect(Collectors.toList());

        PopulationResponse populationResponse = new PopulationResponse();
        populationResponse.setContent(content);
        populationResponse.setPageNumber(populations.getNumber());
        populationResponse.setPageSize(populations.getSize());
        populationResponse.setTotalElements(populations.getTotalElements());
        populationResponse.setTotalPages(populations.getTotalPages());
        populationResponse.setLast(populations.isLast());
        // } catch (DataAccessException e) {
            // throw new DatabaseException("Error retrieving all Population entities", e);
        // }
        return populationResponse;
    }

    @Override
    @Transactional
    public String delete(int id) {
        try {
            return populationDAO.delete(id);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Population with id " + id + " not found");
        }
    }

    public PopulationDTO convertToDTO(Population population) {
        return new PopulationDTO(
                population.getPopulationId(),
                population.getYear().getYear(),
                population.getDistrict().getDistrict(),
                population.getMen(),
                population.getWomen()
        );
    }

}

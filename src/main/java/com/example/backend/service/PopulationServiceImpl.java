package com.example.backend.service;

import com.example.backend.DAO.PopulationDAO;
import com.example.backend.DTO.DistrictDTO;
import com.example.backend.DTO.PopulationDTO;
import com.example.backend.POJO.District;
import com.example.backend.POJO.Population;
import com.example.backend.exception.EntityNotFoundException;
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
    public List<PopulationDTO> findAll() {
//        try {
        List<Population> populations = populationDAO.findAll();
        return populations.stream().map(this::convertToDTO).collect(Collectors.toList());

//        } catch (DataAccessException e) {
//            throw new DatabaseException("Error retrieving all Population entities", e);
//        }
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

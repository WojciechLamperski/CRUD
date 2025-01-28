package com.example.backend.service;

import com.example.backend.DAO.PopulationDAO;
import com.example.backend.POJO.Population;
import com.example.backend.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Population findById(int id) {
        try {
            return populationDAO.findById(id);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Population with id " + id + " not found");
        }
    }

    @Override
    public List<Population> findAll() {
//        try {
        return populationDAO.findAll();
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

}

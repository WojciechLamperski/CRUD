package com.example.backend.service;

import com.example.backend.DAO.YearDAO;
import com.example.backend.POJO.Year;
import com.example.backend.exception.YearNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class YearServiceImpl implements YearService {

    private YearDAO yearDAO;

    public YearServiceImpl(YearDAO theYearDAO) {
        yearDAO = theYearDAO;
    }

    @Override
    @Transactional
    public int save(Year year) {
//        try {
            return yearDAO.save(year);
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Error saving Year entity", e);
//        }
    }

    @Override
    public Year findById(int id) {
        try {
            return yearDAO.findById(id);
        } catch (RuntimeException e) {
            throw new YearNotFoundException("Year with id " + id + " not found");
        }
    }

    @Override
    public List<Year> findAll() {
//        try {
            return yearDAO.findAll();
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Error retrieving all Year entities", e);
//        }
    }

    @Override
    @Transactional
    public int update(Year year) {
//        try {
            return yearDAO.update(year);
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Error updating Year entity", e);
//        }
    }

    @Override
    @Transactional
    public int delete(int id) {
        try {
            return yearDAO.delete(id);
        } catch (RuntimeException e) {
            throw new YearNotFoundException("Year with id " + id + " not found");
        }
    }
}

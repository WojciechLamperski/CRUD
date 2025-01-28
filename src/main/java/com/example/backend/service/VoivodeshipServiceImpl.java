package com.example.backend.service;

import com.example.backend.DAO.VoivodeshipDAO;
import com.example.backend.POJO.Voivodeship;
import com.example.backend.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class VoivodeshipServiceImpl implements VoivodeshipService {

    private VoivodeshipDAO voivodeshipDAO;

    public VoivodeshipServiceImpl(VoivodeshipDAO theVoivodeshipDAO) {
        voivodeshipDAO = theVoivodeshipDAO;
    }

    @Override
    @Transactional
    public String save(Voivodeship voivodeship) {
//        try {
        return voivodeshipDAO.save(voivodeship);
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Error saving Voivodeship entity", e);
//        }
    }

    @Override
    public Voivodeship findById(int id) {
        try {
            return voivodeshipDAO.findById(id);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Voivodeship with id " + id + " not found");
        }
    }

    @Override
    public List<Voivodeship> findAll() {
//        try {
        return voivodeshipDAO.findAll();
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Error retrieving all Voivodeship entities", e);
//        }
    }

    @Override
    @Transactional
    public String delete(int id) {
        try {
            return voivodeshipDAO.delete(id);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("Voivodeship with id " + id + " not found");
        }
    }

}

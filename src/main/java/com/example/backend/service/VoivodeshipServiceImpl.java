package com.example.backend.service;

import com.example.backend.DAO.VoivodeshipDAO;
import com.example.backend.DTO.PopulationDTO;
import com.example.backend.DTO.VoivodeshipResponse;
import com.example.backend.POJO.Voivodeship;
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
public class VoivodeshipServiceImpl implements VoivodeshipService {

    private VoivodeshipDAO voivodeshipDAO;

    public VoivodeshipServiceImpl(VoivodeshipDAO theVoivodeshipDAO) {
        voivodeshipDAO = theVoivodeshipDAO;
    }

    @Override
    @Transactional
    public String save(Voivodeship voivodeship) {
        // try {
        return voivodeshipDAO.save(voivodeship);
        // } catch (DataAccessException e) {
            // throw new DatabaseException("Error saving Voivodeship entity", e);
        // }
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
    public VoivodeshipResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // try {
        Page<Voivodeship> voivodeships = voivodeshipDAO.findAll(pageable, sort);
        List<Voivodeship> content = voivodeships.stream().collect(Collectors.toList());

        VoivodeshipResponse voivodeshipResponse = new VoivodeshipResponse();
        voivodeshipResponse.setContent(content);
        voivodeshipResponse.setPageNumber(voivodeships.getNumber());
        voivodeshipResponse.setPageSize(voivodeships.getSize());
        voivodeshipResponse.setTotalElements(voivodeships.getTotalElements());
        voivodeshipResponse.setTotalPages(voivodeships.getTotalPages());
        voivodeshipResponse.setLast(voivodeships.isLast());
        // } catch (DataAccessException e) {
            // throw new DatabaseException("Error retrieving all Voivodeship entities", e);
        // }
        return voivodeshipResponse;
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

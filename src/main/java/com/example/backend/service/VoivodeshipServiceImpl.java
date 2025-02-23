package com.example.backend.service;

import com.example.backend.DAO.VoivodeshipDAO;
import com.example.backend.DTO.VoivodeshipResponse;
import com.example.backend.POJO.Voivodeship;
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
public class VoivodeshipServiceImpl implements VoivodeshipService {

    private final VoivodeshipDAO voivodeshipDAO;

    public VoivodeshipServiceImpl(VoivodeshipDAO theVoivodeshipDAO) {
        voivodeshipDAO = theVoivodeshipDAO;
    }

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("voivodeshipId", "voivodeship");

    @Override
    @Transactional
    public String save(Voivodeship voivodeship) {

        if(voivodeship.getVoivodeshipId() != 0 && voivodeshipDAO.findById(voivodeship.getVoivodeshipId()) == null){
            throw new EntityNotFoundException("Voivodeship which you're trying to update was not found");
        }
        return voivodeshipDAO.save(voivodeship);
    }

    @Override
    public Voivodeship findById(int id) {

        Voivodeship voivodeship = voivodeshipDAO.findById(id);
        if (voivodeship == null) {
            throw new EntityNotFoundException("Voivodeship not found");
        }
        return voivodeshipDAO.findById(id);
    }

    @Override
    public VoivodeshipResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Voivodeship> voivodeships = voivodeshipDAO.findAll(pageable, sort);
        List<Voivodeship> content = voivodeships.stream().collect(Collectors.toList());

        return new VoivodeshipResponse(
                content, voivodeships.getNumber(), voivodeships.getSize(), voivodeships.getTotalElements(), voivodeships.getTotalPages(), voivodeships.isLast()
        );
    }

    @Override
    @Transactional
    public String delete(int id) {
        Voivodeship voivodeship = voivodeshipDAO.findById(id);
        if (voivodeship == null) {
            throw new EntityNotFoundException("Voivodeship not found");
        }
        return voivodeshipDAO.delete(id);

    }

}

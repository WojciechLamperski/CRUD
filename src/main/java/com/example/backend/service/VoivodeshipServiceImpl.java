package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.VoivodeshipRepository;
import com.example.backend.entity.VoivodeshipEntity;
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
public class VoivodeshipServiceImpl implements VoivodeshipService {

    private Logger logger = LoggerFactory.getLogger(VoivodeshipServiceImpl.class);

    private final VoivodeshipRepository voivodeshipRepository;

    public VoivodeshipServiceImpl(VoivodeshipRepository theVoivodeshipRepository) {
        voivodeshipRepository = theVoivodeshipRepository;
    }

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("voivodeshipId", "voivodeship");

    @Override
    @Transactional
    public VoivodeshipEntity save(VoivodeshipEntity voivodeship) {
        logger.info("service received request to save / update voivodeship {}", voivodeship);
        if(voivodeship.getVoivodeshipId() != 0 && voivodeshipRepository.findById(voivodeship.getVoivodeshipId()) == null){
            throw new EntityNotFoundException("Voivodeship which you're trying to update was not found");
        }
        return voivodeshipRepository.save(voivodeship);
    }

    @Override
    public VoivodeshipEntity findById(int id) {
        logger.info("service received request to find voivodeship by Id");
        VoivodeshipEntity voivodeship = voivodeshipRepository.findById(id);
        if (voivodeship == null) {
            throw new EntityNotFoundException("Voivodeship not found");
        }
        return voivodeshipRepository.findById(id);
    }

    @Override
    public VoivodeshipResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        logger.info("service received request to find all voivodeships");
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

        Page<VoivodeshipEntity> voivodeships = voivodeshipRepository.findAll(pageable, sort);
        List<VoivodeshipEntity> content = voivodeships.stream().collect(Collectors.toList());

        return convertToResponse(pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    @Transactional
    public String delete(int id) {
        logger.info("service received request to delete voivodeship");
        VoivodeshipEntity voivodeship = voivodeshipRepository.findById(id);
        if (voivodeship == null) {
            logger.info("voivodeship not found in service");
            throw new EntityNotFoundException("Voivodeship not found");
        }
        return voivodeshipRepository.delete(id);

    }

    public VoivodeshipModel convertToModel(VoivodeshipEntity district) {
        logger.info("converting voivodeship to model in service");
        VoivodeshipModel voivodeshipModel = new VoivodeshipModel();
        voivodeshipModel.setVoivodeshipId(district.getVoivodeshipId());
        voivodeshipModel.setVoivodeship(district.getVoivodeship());

        return voivodeshipModel;
    }

    public VoivodeshipResponse convertToResponse(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        logger.info("finding all voivodeships and converting them to response in service");
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<VoivodeshipEntity> voivodeships;
        voivodeships = voivodeshipRepository.findAll(pageable, sort);

        List<VoivodeshipModel> content = voivodeships.stream().map(this::convertToModel).collect(Collectors.toList());

        return new VoivodeshipResponse(
                content, voivodeships.getNumber(), voivodeships.getSize(), voivodeships.getTotalElements(), voivodeships.getTotalPages(), voivodeships.isLast()
        );
    }

}

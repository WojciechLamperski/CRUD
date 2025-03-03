package com.example.backend.service;

import com.example.backend.repository.PopulationRepository;
import com.example.backend.model.DistrictModel;
import com.example.backend.model.PopulationResponse;
import com.example.backend.model.PopulationModel;
import com.example.backend.entity.PopulationEntity;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.exception.InvalidSortFieldException;
import com.example.backend.exception.ReferencedEntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PopulationServiceImpl implements PopulationService {

    private final PopulationRepository populationRepository;

    public PopulationServiceImpl(PopulationRepository thePopulationRepository) {
        populationRepository = thePopulationRepository;
    }

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("populationId", "yearId", "districtId", "men", "women");

    @Override
    @Transactional
    public String save(PopulationEntity population) {

        if(population.getPopulationId() != 0 && populationRepository.findById(population.getPopulationId()) == null){
            throw new EntityNotFoundException("Population which you're trying to update was not found");
        }
        if(population.getYearId() != null){
            if(population.getPopulationId() != 0 & populationRepository.findById(population.getYearId()) == null) {
                throw new ReferencedEntityNotFoundException("Year with this Id not found");
            }
        }
        if(population.getDistrictId() != null){
            if(population.getPopulationId() != 0 &  populationRepository.findById(population.getDistrictId()) == null){
                throw new ReferencedEntityNotFoundException("District with this Id not found");
            }
        }
        return populationRepository.save(population);
    }

    @Override
    public PopulationModel findById(int id) {
        PopulationEntity population = populationRepository.findById(id);
        if (population == null) {
            throw new EntityNotFoundException("Population not found");
        }
        return convertToModel(population);
    }

    @Override
    public PopulationResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        return convertToResponse(null, null, null, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    public PopulationResponse findAllInDistrict(int districtId, int pageNumber, int pageSize, String sortBy, String sortDirection) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        return convertToResponse(districtId, null, null, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    public PopulationResponse findAllInYear(int yearId, int pageNumber, int pageSize, String sortBy, String sortDirection) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        return convertToResponse(null, yearId, null, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    public PopulationResponse findAllInVoivodeship(int voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        return convertToResponse(null, null, voivodeshipId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    @Transactional
    public String delete(int id) {

        PopulationEntity population = populationRepository.findById(id);
        if (population == null) {
            throw new EntityNotFoundException("District not found");
        }
        return populationRepository.delete(id);
    }

    public PopulationModel convertToModel(PopulationEntity population) {

        DistrictModel districtModel = new DistrictModel();

        // Check since district can be null in populations
        if(population.getDistrict() != null) {
            districtModel.setDistrictId(population.getDistrict().getDistrictId());
            districtModel.setDistrict(population.getDistrict().getDistrict());
            districtModel.setVoivodeship(population.getDistrict().getVoivodeship().getVoivodeship());
        }else{
            districtModel.setDistrict(null);
        }

        PopulationModel populationModel = new PopulationModel();
        populationModel.setPopulationId(population.getPopulationId());

        // Check since year can be null in populations
        if(population.getYear() != null) {
            populationModel.setYear(population.getYear().getYear());
        }else{
            populationModel.setYear(null);
        }

        populationModel.setDistrict(districtModel);
        populationModel.setMen(population.getMen());
        populationModel.setWomen(population.getWomen());

        return populationModel;
    }

    public PopulationResponse convertToResponse(Integer districtId, Integer yearId, Integer voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Determine the correct query to use based on the provided parameters
        Page<PopulationEntity> populations;
        if (districtId != null) {
            populations = populationRepository.findAllInDistrict(pageable, sort, districtId);
        } else if (yearId != null) {
            populations = populationRepository.findAllInYear(pageable, sort, yearId);
        } else if (voivodeshipId != null) {
            populations = populationRepository.findAllInVoivodeship(pageable, sort, voivodeshipId);
        } else {
            populations = populationRepository.findAll(pageable, sort);
        }

        List<PopulationModel> content = populations.stream().map(this::convertToModel).collect(Collectors.toList());

        return new PopulationResponse(
                content, populations.getNumber(), populations.getSize(), populations.getTotalElements(), populations.getTotalPages(), populations.isLast()
        );
    }


}

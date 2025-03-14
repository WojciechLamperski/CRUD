package com.example.backend.service;

import com.example.backend.entity.DistrictEntity;
import com.example.backend.entity.VoivodeshipEntity;
import com.example.backend.entity.YearEntity;
import com.example.backend.model.PopulationRequest;
import com.example.backend.repository.DistrictRepository;
import com.example.backend.repository.PopulationRepository;
import com.example.backend.model.DistrictModel;
import com.example.backend.model.PopulationResponse;
import com.example.backend.model.PopulationModel;
import com.example.backend.entity.PopulationEntity;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.exception.InvalidSortFieldException;
import com.example.backend.exception.ReferencedEntityNotFoundException;
import com.example.backend.repository.YearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private Logger logger = LoggerFactory.getLogger(PopulationServiceImpl.class);

    private final PopulationRepository populationRepository;
    private final YearRepository yearRepository;
    private final DistrictRepository districtRepository;


    @Autowired
    public PopulationServiceImpl(
            PopulationRepository thePopulationRepository,
            YearRepository theYearRepository,
            DistrictRepository theDistrictRepository
    ) {
        populationRepository = thePopulationRepository;
        yearRepository = theYearRepository;
        districtRepository = theDistrictRepository;
    }

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("populationId", "yearId", "districtId", "men", "women");

    @Override
    @Transactional
    public PopulationModel save(PopulationRequest population) {
        logger.info("service received request to save / update population {}", population);
        if(population.getPopulationId() != 0 && populationRepository.findById(population.getPopulationId()) == null){
            throw new EntityNotFoundException("Population which you're trying to update was not found");
        }
        return convertToModel(populationRepository.save(convertToEntity(population)));
    }

    @Override
    public PopulationModel findById(int id) {
        logger.info("service received request to find population by Id");
        PopulationEntity population = populationRepository.findById(id);
        if (population == null) {
            throw new EntityNotFoundException("Population not found");
        }
        return convertToModel(population);
    }

    @Override
    public PopulationResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        logger.info("service received request to find all populations");
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        return convertToResponse(null, null, null, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    public PopulationResponse findAllInDistrict(int districtId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        logger.info("service received request to find all populations in specified district");
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        return convertToResponse(districtId, null, null, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    public PopulationResponse findAllInYear(int yearId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        logger.info("service received request to find all populations in specified year");
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        return convertToResponse(null, yearId, null, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    public PopulationResponse findAllInVoivodeship(int voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        logger.info("service received request to find all populations in specified voivodeship");
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        return convertToResponse(null, null, voivodeshipId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    @Transactional
    public String delete(int id) {
        logger.info("service received request to delete population");
        PopulationEntity population = populationRepository.findById(id);
        if (population == null) {
            throw new EntityNotFoundException("District not found");
        }
        return populationRepository.delete(id);
    }

    public PopulationEntity convertToEntity(PopulationRequest population) {
        logger.info("converting population request to entity in population");

        PopulationEntity populationEntity = new PopulationEntity();
        VoivodeshipEntity voivodeshipEntity = null;
        DistrictEntity districtEntity = null;
        YearEntity yearEntity = null;

        if(population.getDistrictId() != null){
            districtEntity = districtRepository.findById(population.getDistrictId()).orElse(null);
        }
        if(population.getDistrictId() != null & voivodeshipEntity == null){
            throw new ReferencedEntityNotFoundException("District with this Id not found");
        }
        if(population.getYearId() != null){
            yearEntity = yearRepository.findById(population.getYearId()).orElse(null);
        }
        if(population.getYearId() != null & voivodeshipEntity == null){
            throw new ReferencedEntityNotFoundException("Year with this Id not found");
        }

        populationEntity.setPopulationId(population.getPopulationId());
        populationEntity.setMen(population.getMen());
        populationEntity.setWomen(population.getWomen());
        populationEntity.setDistrict(districtEntity);
        populationEntity.setYear(yearEntity);

        populationEntity.setYearId(population.getYearId());
        populationEntity.setDistrictId(population.getDistrictId());

        return populationEntity;

    }


    public PopulationModel convertToModel(PopulationEntity population) {
        logger.info("converting district to model in population");
        DistrictModel districtModel = new DistrictModel();

        // Check since district can be null in populations
        if(population.getDistrict() != null) {
            districtModel.setDistrictId(population.getDistrictId());
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
        logger.info("finding all populations and converting them to response in service");
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

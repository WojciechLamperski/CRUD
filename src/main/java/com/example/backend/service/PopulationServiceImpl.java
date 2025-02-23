package com.example.backend.service;

import com.example.backend.DAO.PopulationDAO;
import com.example.backend.DTO.DistrictDTO;
import com.example.backend.DTO.PopulationResponse;
import com.example.backend.DTO.PopulationDTO;
import com.example.backend.POJO.Population;
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

    private final PopulationDAO populationDAO;

    public PopulationServiceImpl(PopulationDAO thePopulationDAO) {
        populationDAO = thePopulationDAO;
    }

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("populationId", "yearId", "districtId", "men", "women");

    @Override
    @Transactional
    public String save(Population population) {

        if(population.getPopulationId() != 0 && populationDAO.findById(population.getPopulationId()) == null){
            throw new EntityNotFoundException("Population which you're trying to update was not found");
        }
        if(population.getYearId() != null){
            if(population.getPopulationId() != 0 & populationDAO.findById(population.getYearId()) == null) {
                throw new ReferencedEntityNotFoundException("Year with this Id not found");
            }
        }
        if(population.getDistrictId() != null){
            if(population.getPopulationId() != 0 &  populationDAO.findById(population.getDistrictId()) == null){
                throw new ReferencedEntityNotFoundException("District with this Id not found");
            }
        }
        return populationDAO.save(population);
    }

    @Override
    public PopulationDTO findById(int id) {
        Population population = populationDAO.findById(id);
        if (population == null) {
            throw new EntityNotFoundException("Population not found");
        }
        return convertToDTO(population);
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

        Population population = populationDAO.findById(id);
        if (population == null) {
            throw new EntityNotFoundException("District not found");
        }
        return populationDAO.delete(id);
    }

    public PopulationDTO convertToDTO(Population population) {

        DistrictDTO districtDTO = new DistrictDTO();

        // Check since district can be null in populations
        if(population.getDistrict() != null) {
            districtDTO.setDistrictId(population.getDistrict().getDistrictId());
            districtDTO.setDistrict(population.getDistrict().getDistrict());
            districtDTO.setVoivodeship(population.getDistrict().getVoivodeship().getVoivodeship());
        }else{
            districtDTO.setDistrict(null);
        }

        PopulationDTO populationDTO = new PopulationDTO();
        populationDTO.setPopulationId(population.getPopulationId());

        // Check since year can be null in populations
        if(population.getYear() != null) {
            populationDTO.setYear(population.getYear().getYear());
        }else{
            populationDTO.setYear(null);
        }

        populationDTO.setDistrict(districtDTO);
        populationDTO.setMen(population.getMen());
        populationDTO.setWomen(population.getWomen());

        return populationDTO;
    }

    public PopulationResponse convertToResponse(Integer districtId, Integer yearId, Integer voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Determine the correct query to use based on the provided parameters
        Page<Population> populations;
        if (districtId != null) {
            populations = populationDAO.findAllInDistrict(pageable, sort, districtId);
        } else if (yearId != null) {
            populations = populationDAO.findAllInYear(pageable, sort, yearId);
        } else if (voivodeshipId != null) {
            populations = populationDAO.findAllInVoivodeship(pageable, sort, voivodeshipId);
        } else {
            populations = populationDAO.findAll(pageable, sort);
        }

        List<PopulationDTO> content = populations.stream().map(this::convertToDTO).collect(Collectors.toList());

        return new PopulationResponse(
                content, populations.getNumber(), populations.getSize(), populations.getTotalElements(), populations.getTotalPages(), populations.isLast()
        );
    }


}

package com.example.backend.service;

import com.example.backend.DAO.PopulationDAO;
import com.example.backend.DTO.DistrictDTO;
import com.example.backend.DTO.PopulationResponse;
import com.example.backend.DTO.PopulationDTO;
import com.example.backend.POJO.Population;
import com.example.backend.exception.EntityNotFoundException;
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

    private PopulationDAO populationDAO;

    public PopulationServiceImpl(PopulationDAO thePopulationDAO) {
        populationDAO = thePopulationDAO;
    }

    @Override
    @Transactional
    public String save(Population population) {

        if(population.getPopulationId() != 0 && populationDAO.findById(population.getPopulationId()) == null){
            throw new EntityNotFoundException("Population which you're trying to update was not found");
        }
        if(population.getYearId() != null){
            if(populationDAO.findById(population.getYearId()) == null) {
                throw new ReferencedEntityNotFoundException("Year with this Id not found");
            }
        }
        if(population.getDistrictId() != null){
            if(populationDAO.findById(population.getDistrictId()) == null){
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
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Population> populations = populationDAO.findAll(pageable, sort);
        List<PopulationDTO> content = populations.stream().map(this::convertToDTO).collect(Collectors.toList());

        PopulationResponse populationResponse = new PopulationResponse(
                content, populations.getNumber(), populations.getSize(), populations.getTotalElements(), populations.getTotalPages(), populations.isLast()
        );

        return populationResponse;
    }

    @Override
    public PopulationResponse findAllInDistrict(int districtId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Population> populations = populationDAO.findAllInDistrict(pageable, sort, districtId);
        List<PopulationDTO> content = populations.stream().map(this::convertToDTO).collect(Collectors.toList());

        PopulationResponse populationResponse = new PopulationResponse(
                content, populations.getNumber(), populations.getSize(), populations.getTotalElements(), populations.getTotalPages(), populations.isLast()
        );

        return populationResponse;
    }

    @Override
    public PopulationResponse findAllInYear(int yearId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Population> populations = populationDAO.findAllInYear(pageable, sort, yearId);
        List<PopulationDTO> content = populations.stream().map(this::convertToDTO).collect(Collectors.toList());

        PopulationResponse populationResponse = new PopulationResponse(
                content, populations.getNumber(), populations.getSize(), populations.getTotalElements(), populations.getTotalPages(), populations.isLast()
        );

        return populationResponse;
    }

    @Override
    public PopulationResponse findAllInVoivodeship(int voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Population> populations = populationDAO.findAllInVoivodeship(pageable, sort, voivodeshipId);

        List<PopulationDTO> content = populations.stream().map(this::convertToDTO).collect(Collectors.toList());

        PopulationResponse populationResponse = new PopulationResponse(
                content, populations.getNumber(), populations.getSize(), populations.getTotalElements(), populations.getTotalPages(), populations.isLast()
        );

        return populationResponse;
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
        districtDTO.setDistrictId(population.getDistrict().getDistrictId());

        // Check since district can be null in populations
        if(population.getDistrict() != null) {
            districtDTO.setDistrict(population.getDistrict().getDistrict());
        }else{
            districtDTO.setDistrict(null);
        }

        districtDTO.setVoivodeship(population.getDistrict().getVoivodeship().getVoivodeship());


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

}

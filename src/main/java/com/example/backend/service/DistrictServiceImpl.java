package com.example.backend.service;

import com.example.backend.repository.DistrictRepository;
import com.example.backend.model.DistrictModel;
import com.example.backend.model.DistrictResponse;
import com.example.backend.entity.DistrictEntity;
import com.example.backend.entity.VoivodeshipEntity;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.exception.InvalidSortFieldException;
import com.example.backend.exception.ReferencedEntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DistrictServiceImpl implements DistrictService {

    private Logger logger = LoggerFactory.getLogger(DistrictServiceImpl.class);

    private final DistrictRepository districtRepository;

    public DistrictServiceImpl(DistrictRepository theDistrictRepository) {
        districtRepository = theDistrictRepository;
    }

    private static final List<String> ALLOWED_SORT_FIELDS = List.of("districtId", "district");

    @Override
    @Transactional
    public DistrictModel save(DistrictEntity district) {
        logger.info("service received request to save / update district {}", district);
        if(district.getDistrictId() != 0 && districtRepository.findById(district.getDistrictId()) == null){
            throw new EntityNotFoundException("District which you're trying to update was not found");
        }
        if(district.getVoivodeshipId() != null){
            if(district.getDistrictId() != 0 & districtRepository.findById(district.getVoivodeshipId()) == null){
                throw new ReferencedEntityNotFoundException("Voivodeship with this Id not found");
            }
        }
        return convertToModel(districtRepository.save(district));
    }

    @Override
    public DistrictModel findById(int id) {
        logger.info("service received request to find district by Id");
        DistrictEntity district = districtRepository.findById(id);
        if (district == null) {
            throw new EntityNotFoundException("District not found");
        }
        return convertToModel(district);
    }

    @Override
    public DistrictResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        logger.info("service received request to find all districts");
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        return convertToResponse(null, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    public DistrictResponse findAllInVoivodeship(int voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection){
        logger.info("service received request to find all districts in specified voivodeship");
        if (!ALLOWED_SORT_FIELDS.contains(sortBy)) {
            throw new InvalidSortFieldException("Invalid sort field: " + sortBy + ". Allowed fields: " + ALLOWED_SORT_FIELDS);
        }

        return convertToResponse(voivodeshipId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @Override
    @Transactional
    public String delete(int id) {
        logger.info("service received request to delete district");
        DistrictEntity district = districtRepository.findById(id);
        if (district == null) {
            throw new EntityNotFoundException("District not found");
        }
        return districtRepository.delete(id);
    }

    public DistrictModel convertToModel(DistrictEntity district) {
        logger.info("converting district to model in service");
        VoivodeshipEntity voivodeship = district.getVoivodeship();

        DistrictModel districtModel = new DistrictModel();
        districtModel.setDistrictId(district.getDistrictId());
        districtModel.setDistrict(district.getDistrict());

        // Check since district can be null in populations
        if(voivodeship != null) {
            districtModel.setVoivodeship(voivodeship.getVoivodeship());
        }else{
            districtModel.setVoivodeship(null);
        }

        districtModel.setVoivodeship(Objects.requireNonNull(voivodeship).getVoivodeship());
        return districtModel;
    }

    public DistrictResponse convertToResponse(Integer voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        logger.info("finding all districts and converting them to response in service");
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Fetch districts based on whether voivodeshipId is provided
        Page<DistrictEntity> districts;
        if (voivodeshipId == null) {
            districts = districtRepository.findAll(pageable, sort);
        } else {
            districts = districtRepository.findAllInVoivodeship(pageable, sort, voivodeshipId);
        }

        List<DistrictModel> content = districts.stream().map(this::convertToModel).collect(Collectors.toList());

        return new DistrictResponse(
                content, districts.getNumber(), districts.getSize(), districts.getTotalElements(), districts.getTotalPages(), districts.isLast()
        );
    }

}

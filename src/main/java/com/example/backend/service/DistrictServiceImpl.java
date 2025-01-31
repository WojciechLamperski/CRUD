package com.example.backend.service;

import com.example.backend.DAO.DistrictDAO;
import com.example.backend.DTO.DistrictDTO;
import com.example.backend.DTO.DistrictResponse;
import com.example.backend.POJO.District;
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
public class DistrictServiceImpl implements DistrictService {

    private DistrictDAO districtDAO;

    public DistrictServiceImpl(DistrictDAO theDistrictDAO) {
        districtDAO = theDistrictDAO;
    }

    @Override
    @Transactional
    public String save(District district) {
//        try {
        return districtDAO.save(district);
//        } catch (DataAccessException e) {
//            throw new DatabaseException("Error saving District entity", e);
//        }
    }

    @Override
    public DistrictDTO findById(int id) {
        District district = districtDAO.findById(id);
        return (district != null) ? convertToDTO(district) : null;
    }


    public List<DistrictDTO> findAll() {
        // try {
        List<District> districts = districtDAO.findAll();
        return districts.stream().map(this::convertToDTO).collect(Collectors.toList());
        // } catch (DataAccessException e) {
            // throw new DatabaseException("Error retrieving all District entities", e);
        // }
    }

    @Override
    public DistrictResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // try {
        Page<District> districts = districtDAO.findAll(pageable, sort);
        List<DistrictDTO> content = districts.stream().map(this::convertToDTO).collect(Collectors.toList());

        DistrictResponse districtResponse = new DistrictResponse();
        districtResponse.setContent(content);
        districtResponse.setPageNumber(districts.getNumber());
        districtResponse.setPageSize(districts.getSize());
        districtResponse.setTotalElements(districts.getTotalElements());
        districtResponse.setTotalPages(districts.getTotalPages());
        districtResponse.setLast(districts.isLast());
        // } catch (DataAccessException e) {
            // throw new DatabaseException("Error retrieving all Population entities", e);
        // }
        return districtResponse;
    }


    @Override
    @Transactional
    public String delete(int id) {
        try {
            return districtDAO.delete(id);
        } catch (RuntimeException e) {
            throw new EntityNotFoundException("District with id " + id + " not found");
        }
    }

    public DistrictDTO convertToDTO(District district) {
        return new DistrictDTO(
                district.getDistrictId(),
                district.getDistrict(),
                district.getVoivodeship().getVoivodeship()
        );
    }

}

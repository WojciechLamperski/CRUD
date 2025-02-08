package com.example.backend.service;

import com.example.backend.DAO.DistrictDAO;
import com.example.backend.DTO.DistrictDTO;
import com.example.backend.DTO.DistrictResponse;
import com.example.backend.POJO.District;
import com.example.backend.POJO.Voivodeship;
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
public class DistrictServiceImpl implements DistrictService {

    private DistrictDAO districtDAO;

    public DistrictServiceImpl(DistrictDAO theDistrictDAO) {
        districtDAO = theDistrictDAO;
    }

    @Override
    @Transactional
    public String save(District district) {

        if(district.getDistrictId() != 0 && districtDAO.findById(district.getDistrictId()) == null){
            throw new EntityNotFoundException("District which you're trying to update was not found");
        }
        if(district.getVoivodeshipId() != null){
            if(districtDAO.findById(district.getVoivodeshipId()) == null){
                throw new ReferencedEntityNotFoundException("Voivodeship with this Id not found");
            }
        }
        return districtDAO.save(district);
    }

    @Override
    public DistrictDTO findById(int id) {

        District district = districtDAO.findById(id);
        if (district == null) {
            throw new EntityNotFoundException("District not found");
        }
        return convertToDTO(district);
    }

    @Override
    public DistrictResponse findAll(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<District> districts = districtDAO.findAll(pageable, sort);
        List<DistrictDTO> content = districts.stream().map(this::convertToDTO).collect(Collectors.toList());

        DistrictResponse districtResponse = new DistrictResponse();
        districtResponse.setContent(content);
        districtResponse.setPageNumber(districts.getNumber());
        districtResponse.setPageSize(districts.getSize());
        districtResponse.setTotalElements(districts.getTotalElements());
        districtResponse.setTotalPages(districts.getTotalPages());
        districtResponse.setLast(districts.isLast());

        return districtResponse;
    }

    @Override
    public DistrictResponse findAllInVoivodeship(int voivodeshipId, int pageNumber, int pageSize, String sortBy, String sortDirection){
        int maxPageSize = 100;  // Prevent excessive page sizes
        pageSize = Math.min(pageSize, maxPageSize);

        // Determine sorting direction
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<District> districts = districtDAO.findAllInVoivodeship(pageable, sort, voivodeshipId);
        List<DistrictDTO> content = districts.stream().map(this::convertToDTO).collect(Collectors.toList());

        DistrictResponse districtResponse = new DistrictResponse();
        districtResponse.setContent(content);
        districtResponse.setPageNumber(districts.getNumber());
        districtResponse.setPageSize(districts.getSize());
        districtResponse.setTotalElements(districts.getTotalElements());
        districtResponse.setTotalPages(districts.getTotalPages());
        districtResponse.setLast(districts.isLast());

        return districtResponse;
    }

    @Override
    @Transactional
    public String delete(int id) {

        District district = districtDAO.findById(id);
        if (district == null) {
            throw new EntityNotFoundException("District not found");
        }
        return districtDAO.delete(id);
    }

    public DistrictDTO convertToDTO(District district) {

        Voivodeship voivodeship = district.getVoivodeship();

        DistrictDTO districtDTO = new DistrictDTO();
        districtDTO.setDistrictId(district.getDistrictId());
        districtDTO.setDistrict(district.getDistrict());

        // Check since district can be null in populations
        if(voivodeship != null) {
            districtDTO.setVoivodeship(voivodeship.getVoivodeship());
        }else{
            districtDTO.setVoivodeship(null);
        }

        districtDTO.setVoivodeship(voivodeship.getVoivodeship());
        return districtDTO;
    }

}

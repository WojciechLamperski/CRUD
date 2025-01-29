package com.example.backend.service;

import com.example.backend.DAO.DistrictDAO;
import com.example.backend.DTO.DistrictDTO;
import com.example.backend.POJO.District;
import com.example.backend.exception.EntityNotFoundException;
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

//    @Override
//    public District findById(int id) {
//        try {
//            return districtDAO.findById(id);
//        } catch (RuntimeException e) {
//            throw new EntityNotFoundException("District with id " + id + " not found");
//        }
//    }

    public DistrictDTO findById(int id) {
        District district = districtDAO.findById(id);
        return (district != null) ? convertToDTO(district) : null;
    }

//    @Override
//    public List<District> findAll() {
////        try {
//        return districtDAO.findAll();
////        } catch (DataAccessException e) {
////            throw new DatabaseException("Error retrieving all District entities", e);
////        }
//    }

    public List<DistrictDTO> findAll() {
        List<District> districts = districtDAO.findAll();
        return districts.stream().map(this::convertToDTO).collect(Collectors.toList());
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

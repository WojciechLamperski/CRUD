package com.example.backend.DAO;

import com.example.backend.POJO.District;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class DistrictDAOImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DistrictDAOImpl districtDAO;

    private District district1;
    private District district2;

    @BeforeEach
    void setUp() {
        // Create and persist test data
        district1 = new District();
        district1.setDistrict("wymyślony");

        district2 = new District();
        district2.setDistrict("wymyślony2");

        entityManager.persist(district1);
        entityManager.persist(district2);
        entityManager.flush();
    }

    @Test
    public void testFindAllDistricts() {
        int pageNumber = 1;
        int pageSize = 15;
        String sortBy = "district";
        Sort.Direction direction = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Sort sort = Sort.by(direction, sortBy);

        Page<District> district = districtDAO.findAll(pageable, sort);
        assertThat(district).isNotNull();

        // Verify pagination details
        assertThat(district.getNumber()).isEqualTo(pageNumber);
        assertThat(district.getSize()).isEqualTo(pageSize);
        assertThat(district.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<District> content = district.getContent();
        assertThat(content).isNotEmpty();
    }

    @Test
    public void testFindAllDistrictsInVoivodeship(){
        int pageNumber = 1;
        int pageSize = 15;
        int VoivodeshipId = 1;
        String sortBy = "district";
        Sort.Direction direction = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Sort sort = Sort.by(direction, sortBy);

        Page<District> district = districtDAO.findAllInVoivodeship(pageable, sort, VoivodeshipId);

        assertThat(district).isNotNull();

        // Verify pagination details
        assertThat(district.getNumber()).isEqualTo(pageNumber);
        assertThat(district.getSize()).isEqualTo(pageSize);
        assertThat(district.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<District> content = district.getContent();
        assertThat(content).isNotEmpty();

    }

    @Test
    public void testSaveDistrict() {
        District newDistrict = new District();
        newDistrict.setDistrict("wymyślony3");

        String result = districtDAO.save(newDistrict);

        assertThat(result).contains("saved successfully");
        assertThat(newDistrict.getDistrictId()).isNotNull();
    }

    @Test
    public void testFindById() {
        District foundDistrict = districtDAO.findById(district1.getDistrictId());

        assertThat(foundDistrict).isNotNull();
        assertThat(foundDistrict.getDistrict()).isEqualTo(district1.getDistrict());
    }

    @Test
    public void testFindById_NotFound() {
        District foundDistrict = districtDAO.findById(999);
        assertThat(foundDistrict).isNull();
    }

    @Test
    public void testDeleteDistrict() {
        String result = districtDAO.delete(district1.getDistrictId());

        assertThat(result).isEqualTo("District successfully deleted");

        // Verify that the district is deleted
        District deletedDistrict = entityManager.find(District.class, district1.getDistrictId());
        assertThat(deletedDistrict).isNull();
    }

    @Test
    public void testDeleteDistrict_NotFound() {
        assertThrows(RuntimeException.class, () -> {
            districtDAO.delete(999);
        });
    }
}
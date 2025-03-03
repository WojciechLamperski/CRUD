package com.example.backend.repository;

import com.example.backend.entity.DistrictEntity;
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

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class DistrictRepositoryImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DistrictRepositoryImpl districtRepository;

    private DistrictEntity district1;

    @BeforeEach
    void setUp() {
        // Create and persist test data
        district1 = new DistrictEntity();
        district1.setDistrict("wymyślony");

        DistrictEntity district2 = new DistrictEntity();
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

        Page<DistrictEntity> district = districtRepository.findAll(pageable, sort);
        assertThat(district).isNotNull();

        // Verify pagination details
        assertThat(district.getNumber()).isEqualTo(pageNumber);
        assertThat(district.getSize()).isEqualTo(pageSize);
        assertThat(district.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<DistrictEntity> content = district.getContent();
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

        Page<DistrictEntity> district = districtRepository.findAllInVoivodeship(pageable, sort, VoivodeshipId);

        assertThat(district).isNotNull();

        // Verify pagination details
        assertThat(district.getNumber()).isEqualTo(pageNumber);
        assertThat(district.getSize()).isEqualTo(pageSize);
        assertThat(district.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<DistrictEntity> content = district.getContent();
        assertThat(content).isNotEmpty();

    }

    @Test
    public void testSaveDistrict() {
        DistrictEntity newDistrict = new DistrictEntity();
        newDistrict.setDistrict("wymyślony3");

        String result = districtRepository.save(newDistrict);

        assertThat(result).contains("saved successfully");
    }

    @Test
    public void testFindById() {
        DistrictEntity foundDistrict = districtRepository.findById(district1.getDistrictId());

        assertThat(foundDistrict).isNotNull();
        assertThat(foundDistrict.getDistrict()).isEqualTo(district1.getDistrict());
    }

    @Test
    public void testFindById_NotFound() {
        DistrictEntity foundDistrict = districtRepository.findById(999);
        assertThat(foundDistrict).isNull();
    }

    @Test
    public void testDeleteDistrict() {
        String result = districtRepository.delete(district1.getDistrictId());

        assertThat(result).isEqualTo("District successfully deleted");

        // Verify that the district is deleted
        DistrictEntity deletedDistrict = entityManager.find(DistrictEntity.class, district1.getDistrictId());
        assertThat(deletedDistrict).isNull();
    }

    @Test
    public void testDeleteDistrict_NotFound() {
        assertThrows(RuntimeException.class, () -> districtRepository.delete(999));
    }
}
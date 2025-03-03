package com.example.backend.repository;

import com.example.backend.entity.PopulationEntity;
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
public class PopulationRepositoryImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PopulationRepositoryImpl populationRepository;

    private PopulationEntity population1;

    @BeforeEach
    void setUp() {
        // Create and persist test data
        population1 = new PopulationEntity();
        population1.setDistrictId(1);
        population1.setYearId(1);
        population1.setMen(1234);
        population1.setWomen(4321);

        PopulationEntity population2 = new PopulationEntity();
        population2.setDistrictId(2);
        population2.setYearId(2);
        population2.setMen(9876);
        population2.setWomen(6789);

        entityManager.persist(population1);
        entityManager.persist(population2);
        entityManager.flush();
    }

    @Test
    public void testFindAllPopulations() {
        int pageNumber = 1;
        int pageSize = 15;
        String sortBy = "populationId";
        Sort.Direction direction = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Sort sort = Sort.by(direction, sortBy);

        Page<PopulationEntity> population = populationRepository.findAll(pageable, sort);

        // Check that the result is not null
        assertThat(population).isNotNull();

        // Verify pagination details
        assertThat(population.getNumber()).isEqualTo(pageNumber);
        assertThat(population.getSize()).isEqualTo(pageSize);
        assertThat(population.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<PopulationEntity> content = population.getContent();
        assertThat(content).isNotEmpty();
    }

    @Test
    public void testFindAllPopulationsInVoivodeship(){
        int pageNumber = 1;
        int pageSize = 15;
        int voivodeshipId = 1;
        String sortBy = "populationId";
        Sort.Direction direction = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Sort sort = Sort.by(direction, sortBy);

        Page<PopulationEntity> population = populationRepository.findAllInVoivodeship(pageable, sort, voivodeshipId);

        // Check that the result is not null
        assertThat(population).isNotNull();

        // Verify pagination details
        assertThat(population.getNumber()).isEqualTo(pageNumber);
        assertThat(population.getSize()).isEqualTo(pageSize);
        assertThat(population.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<PopulationEntity> content = population.getContent();
        assertThat(content).isNotEmpty();
    }

    @Test
    public void testFindAllPopulationsInDistrict(){
        int pageNumber = 1;
        int pageSize = 15;
        int districtId = 1;
        String sortBy = "populationId";
        Sort.Direction direction = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Sort sort = Sort.by(direction, sortBy);

        Page<PopulationEntity> population = populationRepository.findAllInDistrict(pageable, sort, districtId);

        // Check that the result is not null
        assertThat(population).isNotNull();

        // Verify pagination details
        assertThat(population.getNumber()).isEqualTo(pageNumber);
        assertThat(population.getSize()).isEqualTo(pageSize);
        assertThat(population.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<PopulationEntity> content = population.getContent();
        assertThat(content).isNotEmpty();
    }

    @Test
    public void testFindAllPopulationsInYear(){
        int pageNumber = 1;
        int pageSize = 15;
        int yearId = 1;
        String sortBy = "populationId";
        Sort.Direction direction = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Sort sort = Sort.by(direction, sortBy);

        Page<PopulationEntity> population = populationRepository.findAllInDistrict(pageable, sort, yearId);


        // Check that the result is not null
        assertThat(population).isNotNull();

        // Verify pagination details
        assertThat(population.getNumber()).isEqualTo(pageNumber);
        assertThat(population.getSize()).isEqualTo(pageSize);
        assertThat(population.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<PopulationEntity> content = population.getContent();
        assertThat(content).isNotEmpty();

    }

    @Test
    public void testSavePopulation() {
        PopulationEntity newPopulation = new PopulationEntity();
        newPopulation.setDistrictId(3);
        newPopulation.setYearId(3);
        newPopulation.setMen(3333);
        newPopulation.setWomen(3333);

        String result = populationRepository.save(newPopulation);

        assertThat(result).contains("saved successfully");
    }

    @Test
    public void testFindById() {
        PopulationEntity foundPopulation = populationRepository.findById(population1.getPopulationId());

        assertThat(foundPopulation).isNotNull();
        assertThat(foundPopulation.getMen()).isEqualTo(population1.getMen());
        assertThat(foundPopulation.getWomen()).isEqualTo(population1.getWomen());
    }

    @Test
    public void testFindById_NotFound() {
        PopulationEntity foundPopulation = populationRepository.findById(9000000);

        assertThat(foundPopulation).isNull();
    }

    @Test
    public void testDeletePopulation() {
        String result = populationRepository.delete(population1.getPopulationId());

        assertThat(result).isEqualTo("Population successfully deleted");

        // Verify that the population is deleted
        PopulationEntity deletedPopulation = entityManager.find(PopulationEntity.class, population1.getPopulationId());
        assertThat(deletedPopulation).isNull();
    }

    @Test
    public void testDeletePopulation_NotFound() {
        assertThrows(RuntimeException.class, () -> populationRepository.delete(9000000));
    }
}

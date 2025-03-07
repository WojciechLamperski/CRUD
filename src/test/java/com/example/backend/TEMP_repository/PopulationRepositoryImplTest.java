package com.example.backend.TEMP_repository;

import com.example.backend.entity.DistrictEntity;
import com.example.backend.entity.PopulationEntity;
import com.example.backend.entity.VoivodeshipEntity;
import com.example.backend.entity.YearEntity;
import com.example.backend.repository.PopulationRepositoryImpl;
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
    private DistrictEntity district1;
    private VoivodeshipEntity voivodeship1;
    private YearEntity year1;

    @BeforeEach
    void setUp() {
        voivodeship1 = new VoivodeshipEntity();
        voivodeship1.setVoivodeship("WYMYŚLONY");
        entityManager.persist(voivodeship1);

        district1 = new DistrictEntity();
        district1.setDistrict("wymyślony");
        district1.setVoivodeshipId(voivodeship1.getVoivodeshipId());
        entityManager.persist(district1);

        year1 = new YearEntity();
        year1.setYear(1222);
        entityManager.persist(year1);

        // Create and persist test data
        population1 = new PopulationEntity();
        population1.setDistrictId(district1.getDistrictId());
        population1.setYearId(year1.getYearId());
        population1.setMen(1234);
        population1.setWomen(4321);
        entityManager.persist(population1);

        PopulationEntity population2 = new PopulationEntity();
        population2.setMen(9876);
        population2.setWomen(6789);
        entityManager.persist(population2);

        entityManager.flush();
    }

    @Test
    public void testFindAllPopulations() {
        int pageNumber = 1;
        int pageSize = 1;
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
        int pageNumber = 0;
        int pageSize = 1;
        int voivodeshipId = voivodeship1.getVoivodeshipId();
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
        int pageNumber = 0;
        int pageSize = 1;
        int districtId = district1.getDistrictId();
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
        int pageNumber = 0;
        int pageSize = 1;
        int yearId = year1.getYearId();
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

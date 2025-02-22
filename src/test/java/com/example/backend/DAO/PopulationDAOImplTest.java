package com.example.backend.DAO;

import com.example.backend.POJO.Population;
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
public class PopulationDAOImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PopulationDAOImpl populationDAO;

    private Population population1;

    @BeforeEach
    void setUp() {
        // Create and persist test data
        population1 = new Population();
        population1.setDistrictId(1);
        population1.setYearId(1);
        population1.setMen(1234);
        population1.setWomen(4321);

        Population population2 = new Population();
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

        Page<Population> population = populationDAO.findAll(pageable, sort);

        // Check that the result is not null
        assertThat(population).isNotNull();

        // Verify pagination details
        assertThat(population.getNumber()).isEqualTo(pageNumber);
        assertThat(population.getSize()).isEqualTo(pageSize);
        assertThat(population.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<Population> content = population.getContent();
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

        Page<Population> population = populationDAO.findAllInVoivodeship(pageable, sort, voivodeshipId);

        // Check that the result is not null
        assertThat(population).isNotNull();

        // Verify pagination details
        assertThat(population.getNumber()).isEqualTo(pageNumber);
        assertThat(population.getSize()).isEqualTo(pageSize);
        assertThat(population.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<Population> content = population.getContent();
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

        Page<Population> population = populationDAO.findAllInDistrict(pageable, sort, districtId);

        // Check that the result is not null
        assertThat(population).isNotNull();

        // Verify pagination details
        assertThat(population.getNumber()).isEqualTo(pageNumber);
        assertThat(population.getSize()).isEqualTo(pageSize);
        assertThat(population.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<Population> content = population.getContent();
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

        Page<Population> population = populationDAO.findAllInDistrict(pageable, sort, yearId);


        // Check that the result is not null
        assertThat(population).isNotNull();

        // Verify pagination details
        assertThat(population.getNumber()).isEqualTo(pageNumber);
        assertThat(population.getSize()).isEqualTo(pageSize);
        assertThat(population.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<Population> content = population.getContent();
        assertThat(content).isNotEmpty();

    }

    @Test
    public void testSavePopulation() {
        Population newPopulation = new Population();
        newPopulation.setDistrictId(3);
        newPopulation.setYearId(3);
        newPopulation.setMen(3333);
        newPopulation.setWomen(3333);

        String result = populationDAO.save(newPopulation);

        assertThat(result).contains("saved successfully");
    }

    @Test
    public void testFindById() {
        Population foundPopulation = populationDAO.findById(population1.getPopulationId());

        assertThat(foundPopulation).isNotNull();
        assertThat(foundPopulation.getMen()).isEqualTo(population1.getMen());
        assertThat(foundPopulation.getWomen()).isEqualTo(population1.getWomen());
    }

    @Test
    public void testFindById_NotFound() {
        Population foundPopulation = populationDAO.findById(9000000);

        assertThat(foundPopulation).isNull();
    }

    @Test
    public void testDeletePopulation() {
        String result = populationDAO.delete(population1.getPopulationId());

        assertThat(result).isEqualTo("Population successfully deleted");

        // Verify that the population is deleted
        Population deletedPopulation = entityManager.find(Population.class, population1.getPopulationId());
        assertThat(deletedPopulation).isNull();
    }

    @Test
    public void testDeletePopulation_NotFound() {
        assertThrows(RuntimeException.class, () -> populationDAO.delete(9000000));
    }
}

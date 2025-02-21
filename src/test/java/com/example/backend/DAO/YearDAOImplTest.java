package com.example.backend.DAO;

import com.example.backend.POJO.Year;
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


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class YearDAOImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private YearDAOImpl yearDAO;

    private Year year1;
    private Year year2;

    @BeforeEach
    void setUp() {
        // Create and persist test data
        year1 = new Year();
        year1.setYear(2020);

        year2 = new Year();
        year2.setYear(2021);

        entityManager.persist(year1);
        entityManager.persist(year2);
        entityManager.flush();
    }

    @Test
    public void testFindAllYears() {
        int pageNumber = 1;
        int pageSize = 15;
        String sortBy = "year";
        Sort.Direction direction = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Sort sort = Sort.by(direction, sortBy);

        Page<Year> years = yearDAO.findAll(pageable, sort);
    }

    @Test
    public void testSaveYear() {
        Year newYear = new Year();
        newYear.setYear(2022);

        String result = yearDAO.save(newYear);

        assertThat(result).contains("saved successfully");
        assertThat(newYear.getYearId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Year foundYear = yearDAO.findById(year1.getYearId());

        assertThat(foundYear).isNotNull();
        assertThat(foundYear.getYear()).isEqualTo(year1.getYear());
    }

    @Test
    public void testFindById_NotFound() {
        Year foundYear = yearDAO.findById(999);

        assertThat(foundYear).isNull();
    }

    @Test
    public void testDeleteYear() {
        String result = yearDAO.delete(year1.getYearId());

        assertThat(result).isEqualTo("Year successfully deleted");

        // Verify that the year is deleted
        Year deletedYear = entityManager.find(Year.class, year1.getYearId());
        assertThat(deletedYear).isNull();
    }

    @Test
    public void testDeleteYear_NotFound() {
        assertThrows(RuntimeException.class, () -> {
            yearDAO.delete(999);
        });
    }
}
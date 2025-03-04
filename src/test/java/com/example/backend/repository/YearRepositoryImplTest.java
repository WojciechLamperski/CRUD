package com.example.backend.repository;

import com.example.backend.entity.YearEntity;
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
public class YearRepositoryImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private YearRepositoryImpl yearRepository;

    private YearEntity year1;

    @BeforeEach
    void setUp() {

        // Create and persist test data
        year1 = new YearEntity();
        year1.setYear(1234);

        YearEntity year2 = new YearEntity();
        year2.setYear(4321);

        entityManager.persist(year1);
        entityManager.persist(year2);
        entityManager.flush();
    }

    @Test
    public void testFindAllYears() {
        // Arrange
        int pageNumber = 1;
        int pageSize = 1;
        String sortBy = "year";
        Sort.Direction direction = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Sort sort = Sort.by(direction, sortBy);

        Page<YearEntity> years = yearRepository.findAll(pageable, sort);

        // heck that the result is not null
        assertThat(years).isNotNull();

        // Verify pagination details
        assertThat(years.getNumber()).isEqualTo(pageNumber);
        assertThat(years.getSize()).isEqualTo(pageSize);
        assertThat(years.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify sorting
        List<YearEntity> content = years.getContent();
        if (!content.isEmpty()) {
            for (int i = 1; i < content.size(); i++) {
                // Ensure each year is greater than or equal to the previous one (ascending order)
                assertThat(content.get(i).getYear()).isGreaterThanOrEqualTo(content.get(i - 1).getYear());
            }
        }

        // Verify content (optional, depending on your test data)
        assertThat(content).isNotEmpty();
    }

    @Test
    public void testSaveYear() {
        YearEntity newYear = new YearEntity();
        newYear.setYear(3333);

        String result = yearRepository.save(newYear);

        assertThat(result).contains("saved successfully");
    }

    @Test
    public void testFindById() {
        YearEntity foundYear = yearRepository.findById(year1.getYearId());

        assertThat(foundYear).isNotNull();
        assertThat(foundYear.getYear()).isEqualTo(year1.getYear());
    }

    @Test
    public void testFindById_NotFound() {
        YearEntity foundYear = yearRepository.findById(999);

        assertThat(foundYear).isNull();
    }

    @Test
    public void testDeleteYear() {
        String result = yearRepository.delete(year1.getYearId());

        assertThat(result).isEqualTo("Year successfully deleted");

        // Verify that the year is deleted
        YearEntity deletedYear = entityManager.find(YearEntity.class, year1.getYearId());
        assertThat(deletedYear).isNull();
    }

    @Test
    public void testDeleteYear_NotFound() {
        assertThrows(RuntimeException.class, () -> yearRepository.delete(999));
    }
}
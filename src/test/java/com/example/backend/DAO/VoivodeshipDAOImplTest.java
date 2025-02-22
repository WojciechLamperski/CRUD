package com.example.backend.DAO;

import com.example.backend.POJO.Voivodeship;
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
public class VoivodeshipDAOImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VoivodeshipDAOImpl voivodeshipDAO;

    private Voivodeship voivodeship1;
    private Voivodeship voivodeship2;

    @BeforeEach
    void setUp() {
        // Create and persist test data
        voivodeship1 = new Voivodeship();
        voivodeship1.setVoivodeship("WYMYŚLONE");

        voivodeship2 = new Voivodeship();
        voivodeship2.setVoivodeship("WYMYŚLONE2");

        entityManager.persist(voivodeship1);
        entityManager.persist(voivodeship2);
        entityManager.flush();
    }

    @Test
    public void testFindAllVoivodeships() {
        // Arrange
        int pageNumber = 1;
        int pageSize = 15;
        String sortBy = "voivodeship";
        Sort.Direction direction = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Sort sort = Sort.by(direction, sortBy);

        Page<Voivodeship> voivodeships = voivodeshipDAO.findAll(pageable, sort);

        // Check that the result is not null
        assertThat(voivodeships).isNotNull();

        // Verify pagination details
        assertThat(voivodeships.getNumber()).isEqualTo(pageNumber);
        assertThat(voivodeships.getSize()).isEqualTo(pageSize);
        assertThat(voivodeships.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<Voivodeship> content = voivodeships.getContent();
        assertThat(content).isNotEmpty();
    }

    @Test
    public void testSaveVoivodeship() {
        Voivodeship newVoivodeship = new Voivodeship();
        newVoivodeship.setVoivodeship("WYMYŚLONE3");

        String result = voivodeshipDAO.save(newVoivodeship);

        assertThat(result).contains("saved successfully");
        assertThat(newVoivodeship.getVoivodeshipId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Voivodeship foundVoivodeship = voivodeshipDAO.findById(voivodeship1.getVoivodeshipId());

        assertThat(foundVoivodeship).isNotNull();
        assertThat(foundVoivodeship.getVoivodeship()).isEqualTo(voivodeship1.getVoivodeship());
    }

    @Test
    public void testFindById_NotFound() {
        Voivodeship foundVoivodeship = voivodeshipDAO.findById(999);

        assertThat(foundVoivodeship).isNull();
    }

    @Test
    public void testDeleteVoivodeship() {
        String result = voivodeshipDAO.delete(voivodeship1.getVoivodeshipId());

        assertThat(result).isEqualTo("Voivodeship successfully deleted");

        // Verify that the voivodeship is deleted
        Voivodeship deletedVoivodeship = entityManager.find(Voivodeship.class, voivodeship1.getVoivodeshipId());
        assertThat(deletedVoivodeship).isNull();
    }

    @Test
    public void testDeleteVoivodeship_NotFound() {
        assertThrows(RuntimeException.class, () -> {
            voivodeshipDAO.delete(999);
        });
    }
}
package com.example.backend.repository;

import com.example.backend.entity.VoivodeshipEntity;
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
public class VoivodeshipRepositoryImplTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VoivodeshipRepositoryImpl voivodeshipRepository;

    private VoivodeshipEntity voivodeship1;

    @BeforeEach
    void setUp() {
        // Create and persist test data
        voivodeship1 = new VoivodeshipEntity();
        voivodeship1.setVoivodeship("WYMYŚLONE");

        VoivodeshipEntity voivodeship2 = new VoivodeshipEntity();
        voivodeship2.setVoivodeship("WYMYŚLONE2");

        entityManager.persist(voivodeship1);
        entityManager.persist(voivodeship2);
        entityManager.flush();
    }

    @Test
    public void testFindAllVoivodeships() {
        // Arrange
        int pageNumber = 1;
        int pageSize = 1;
        String sortBy = "voivodeship";
        Sort.Direction direction = Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Sort sort = Sort.by(direction, sortBy);

        Page<VoivodeshipEntity> voivodeships = voivodeshipRepository.findAll(pageable, sort);

        // Check that the result is not null
        assertThat(voivodeships).isNotNull();

        // Verify pagination details
        assertThat(voivodeships.getNumber()).isEqualTo(pageNumber);
        assertThat(voivodeships.getSize()).isEqualTo(pageSize);
        assertThat(voivodeships.getTotalPages()).isGreaterThanOrEqualTo(0);

        // Verify content
        List<VoivodeshipEntity> content = voivodeships.getContent();
        assertThat(content).isNotEmpty();
    }

    @Test
    public void testSaveVoivodeship() {
        VoivodeshipEntity newVoivodeship = new VoivodeshipEntity();
        newVoivodeship.setVoivodeship("WYMYŚLONE3");

        String result = voivodeshipRepository.save(newVoivodeship);

        assertThat(result).contains("saved successfully");
    }

    @Test
    public void testFindById() {
        VoivodeshipEntity foundVoivodeship = voivodeshipRepository.findById(voivodeship1.getVoivodeshipId());

        assertThat(foundVoivodeship).isNotNull();
        assertThat(foundVoivodeship.getVoivodeship()).isEqualTo(voivodeship1.getVoivodeship());
    }

    @Test
    public void testFindById_NotFound() {
        VoivodeshipEntity foundVoivodeship = voivodeshipRepository.findById(999);

        assertThat(foundVoivodeship).isNull();
    }

    @Test
    public void testDeleteVoivodeship() {
        String result = voivodeshipRepository.delete(voivodeship1.getVoivodeshipId());

        assertThat(result).isEqualTo("Voivodeship successfully deleted");

        // Verify that the voivodeship is deleted
        VoivodeshipEntity deletedVoivodeship = entityManager.find(VoivodeshipEntity.class, voivodeship1.getVoivodeshipId());
        assertThat(deletedVoivodeship).isNull();
    }

    @Test
    public void testDeleteVoivodeship_NotFound() {
        assertThrows(RuntimeException.class, () -> voivodeshipRepository.delete(999));
    }
}
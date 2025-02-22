package com.example.backend.sercice;

import com.example.backend.DAO.YearDAO;
import com.example.backend.DTO.YearResponse;
import com.example.backend.POJO.Year;
import com.example.backend.exception.EntityNotFoundException;
import com.example.backend.exception.InvalidSortFieldException;
import com.example.backend.service.YearServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class YearServiceImplTest {

    @Mock
    private YearDAO yearDAO;

    @InjectMocks
    private YearServiceImpl yearService;

    private Year year1;
    private Year year2;

    @BeforeEach
    void setUp() {
        year1 = new Year();
        year1.setYear(2024);

        year2 = new Year();
        year2.setYear(2025);
    }

    @Test
    public void testFindById() {
        // Arrange
        when(yearDAO.findById(1)).thenReturn(year1);

        // Act
        Year foundYear = yearService.findById(1);

        // Assert
        assertThat(foundYear).isNotNull();
        assertThat(foundYear.getYearId()).isEqualTo(1);
        assertThat(foundYear.getYear()).isEqualTo(2024);
        verify(yearDAO, times(1)).findById(1);
    }

    @Test
    public void testFindById_EntityNotFoundException() {
        // Arrange
        when(yearDAO.findById(anyInt())).thenReturn(null);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            yearService.findById(1);
        });

        // Verify that the DAO was called
        verify(yearDAO, times(1)).findById(1);
    }

    @Test
    public void testFindAll() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "year"));
        Page<Year> page = new PageImpl<>(List.of(year1, year2), pageable, 2);

        when(yearDAO.findAll(any(Pageable.class), any(Sort.class))).thenReturn(page);

        // Act
        YearResponse response = yearService.findAll(0, 10, "year", "asc");

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0).getYear()).isEqualTo(2024);
        assertThat(response.getContent().get(1).getYear()).isEqualTo(2025);
        assertThat(response.getPageNumber()).isEqualTo(0);
        assertThat(response.getPageSize()).isEqualTo(10);
        assertThat(response.getTotalElements()).isEqualTo(2);
        assertThat(response.getTotalPages()).isEqualTo(1);
        assertThat(response.isLast()).isTrue();

        // Verify that the DAO was called with the correct parameters
        verify(yearDAO, times(1)).findAll(pageable, Sort.by(Sort.Direction.ASC, "year"));
    }

    @Test
    public void testFindAll_InvalidSortFieldException() {
        // Act & Assert
        assertThrows(InvalidSortFieldException.class, () -> {
            yearService.findAll(0, 10, "invalidField", "asc");
        });

        // Verify that the DAO was never called
        verify(yearDAO, never()).findAll(any(Pageable.class), any(Sort.class));
    }

    @Test
    public void testFindAll_DataTransformation() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "year"));
        Page<Year> page = new PageImpl<>(List.of(year1, year2), pageable, 2);

        when(yearDAO.findAll(any(Pageable.class), any(Sort.class))).thenReturn(page);

        // Act
        YearResponse response = yearService.findAll(0, 10, "year", "asc");

        // Assert
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getContent().get(0).getYearId()).isEqualTo(1);
        assertThat(response.getContent().get(1).getYearId()).isEqualTo(2);
    }

}
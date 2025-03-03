package com.example.backend;

import com.example.backend.repository.DistrictRepository;
import com.example.backend.repository.PopulationRepository;
import com.example.backend.repository.VoivodeshipRepository;
import com.example.backend.repository.YearRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void criticalBeansAreLoaded() {
        assertThat(applicationContext.getBean(YearRepository.class)).isNotNull();
        assertThat(applicationContext.getBean(VoivodeshipRepository.class)).isNotNull();
        assertThat(applicationContext.getBean(PopulationRepository.class)).isNotNull();
        assertThat(applicationContext.getBean(DistrictRepository.class)).isNotNull();
    }
}

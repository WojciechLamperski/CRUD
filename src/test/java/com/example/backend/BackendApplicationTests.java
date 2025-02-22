package com.example.backend;

import com.example.backend.DAO.DistrictDAO;
import com.example.backend.DAO.PopulationDAO;
import com.example.backend.DAO.VoivodeshipDAO;
import com.example.backend.DAO.YearDAO;
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
        assertThat(applicationContext.getBean(YearDAO.class)).isNotNull();
        assertThat(applicationContext.getBean(VoivodeshipDAO.class)).isNotNull();
        assertThat(applicationContext.getBean(PopulationDAO.class)).isNotNull();
        assertThat(applicationContext.getBean(DistrictDAO.class)).isNotNull();
    }
}

package com.example.backend.init;

import com.example.backend.entity.DistrictEntity;
import com.example.backend.entity.PopulationEntity;
import com.example.backend.entity.VoivodeshipEntity;
import com.example.backend.entity.YearEntity;
import com.example.backend.model.TempModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitialDataLoad {

    private Logger logger = LoggerFactory.getLogger(InitialDataLoad.class);

    @Value("${init.data.file.path}")
    private String DATA;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws IOException {
            logger.info("Loading Population data into database from {}", DATA);

            ClassPathResource resource = new ClassPathResource(DATA);
            InputStream inputStream = resource.getInputStream();
            List<TempModel> models = new ObjectMapper().readValue(inputStream, new TypeReference<>() {});

            Set<Integer> existingYears = new HashSet<>();
            Set<String> existingVoivodeships = new HashSet<>();
            Set<String> existingDistricts = new HashSet<>();

            VoivodeshipEntity voivodeshipEntity = null;
            YearEntity yearEntity = null;
            DistrictEntity districtEntity = null;

            for (TempModel model : models) {

                if (!existingYears.contains(model.getYear())) {
                    yearEntity = new YearEntity();
                    yearEntity.setYear(model.getYear());
                    // Save year to db
                    entityManager.persist(yearEntity);
                    // Add voivodeship to the set
                    existingYears.add(model.getYear());
                }

                if (!existingVoivodeships.contains(model.getVoivodeship())) {
                    voivodeshipEntity = new VoivodeshipEntity();
                    voivodeshipEntity.setVoivodeship(model.getVoivodeship());
                    // Save voivodeship to db
                    entityManager.persist(voivodeshipEntity);
                    // Add voivodeship to the set
                    existingVoivodeships.add(model.getVoivodeship());
                }

                // Combine district and voivodeship as a unique key
                String districtKey = model.getDistrict() + "_" + model.getVoivodeship();
                if (!existingDistricts.contains(districtKey)) {
                    districtEntity = new DistrictEntity();
                    districtEntity.setDistrict(model.getDistrict());
                    districtEntity.setVoivodeshipId(voivodeshipEntity.getVoivodeshipId());
                    // Save district to db
                    entityManager.persist(districtEntity);
                    // Add district key to the set
                    existingDistricts.add(districtKey);
                }

                PopulationEntity populationEntity = new PopulationEntity();
                populationEntity.setMen(model.getMen());
                populationEntity.setWomen(model.getWomen());
                populationEntity.setYearId(yearEntity.getYearId());
                populationEntity.setDistrictId(districtEntity.getDistrictId());
                // Save population to db
                entityManager.persist(populationEntity);
            }

            logger.info("All records loaded into database from {}", DATA);

    }

}


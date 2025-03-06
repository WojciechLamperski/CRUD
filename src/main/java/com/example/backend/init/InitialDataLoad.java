package com.example.backend.init;

import com.example.backend.entity.DistrictEntity;
import com.example.backend.entity.PopulationEntity;
import com.example.backend.entity.VoivodeshipEntity;
import com.example.backend.entity.YearEntity;
import com.example.backend.model.TempModel;
import com.example.backend.service.DistrictService;
import com.example.backend.service.PopulationService;
import com.example.backend.service.VoivodeshipService;
import com.example.backend.service.YearService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String DATA = "data.json";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private YearService yearService;

    private VoivodeshipService voivodeshipService;
    private DistrictService districtService;
    private PopulationService populationService;

    @Transactional
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) throws IOException {

            System.out.println("Hi!");

            logger.info("Loading Population data into database from {}", DATA);

            ClassPathResource resource = new ClassPathResource(DATA);
            InputStream inputStream = resource.getInputStream();

            List<TempModel> models = new ObjectMapper().readValue(inputStream, new TypeReference<>() {});

            System.out.println("year from first model " +  models.get(0).getYear());
            System.out.println("Total models loaded: " + models.size());  // Check if the list is empty
            System.out.println("First model: " + models.get(0));  // Check the first element in the list


            Set<Integer> existingYears = new HashSet<>();
            Set<String> existingVoivodeships = new HashSet<>();
            Set<String> existingDistricts = new HashSet<>();

            VoivodeshipEntity voivodeshipEntity = null;
            YearEntity yearEntity = null;
            DistrictEntity districtEntity = null;

            for (TempModel model : models) {

                System.out.println("check");

                if (!existingYears.contains(model.getYear())) {
                    yearEntity = new YearEntity();
                    yearEntity.setYear(model.getYear());
                    entityManager.persist(yearEntity);
                    existingYears.add(model.getYear());
                }

                if (!existingVoivodeships.contains(model.getVoivodeship())) {
                    voivodeshipEntity = new VoivodeshipEntity();
                    voivodeshipEntity.setVoivodeship(model.getVoivodeship());
                    entityManager.persist(voivodeshipEntity);
                    existingVoivodeships.add(model.getVoivodeship()); // Add voivodeship to the set
                }

                String districtKey = model.getDistrict() + "_" + model.getVoivodeship(); // Combine district and voivodeship as a unique key
                if (!existingDistricts.contains(districtKey)) {
                    districtEntity = new DistrictEntity();
                    districtEntity.setDistrict(model.getDistrict());
                    districtEntity.setVoivodeshipId(voivodeshipEntity.getVoivodeshipId());
                    entityManager.persist(districtEntity);
                    existingDistricts.add(districtKey); // Add district key to the set
                }

                PopulationEntity populationEntity = new PopulationEntity();
                populationEntity.setMen(model.getMen());
                populationEntity.setWomen(model.getWomen());
                populationEntity.setYearId(yearEntity.getYearId());
                populationEntity.setDistrictId(districtEntity.getDistrictId());
                entityManager.persist(populationEntity);

            }

//            logger.info("All Cakes loaded into database from {}", cakesDownloadUrl);

    }

}


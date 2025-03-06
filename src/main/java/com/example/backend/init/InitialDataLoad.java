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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class InitialDataLoad {

    private Logger logger = LoggerFactory.getLogger(InitialDataLoad.class);

//    @Value("${init.data.file.name}")
//    private String populationDataJson;

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


            for (TempModel model : models) {

                System.out.println("check");

                YearEntity yearEntity = new YearEntity();
                yearEntity.setYear(model.getYear());
                System.out.println("year: " + yearEntity);
                yearService.save(yearEntity);

//                VoivodeshipEntity voivodeshipEntity = new VoivodeshipEntity();
//                voivodeshipEntity.setVoivodeship(model.getVoivodeship());
//                entityManager.persist(voivodeshipEntity);
//
//                DistrictEntity districtEntity = new DistrictEntity();
//                districtEntity.setDistrict(model.getDistrict());
//                districtEntity.setVoivodeshipId(voivodeshipEntity.getVoivodeshipId());
//                entityManager.persist(districtEntity);
//
//                PopulationEntity populationEntity = new PopulationEntity();
//                populationEntity.setMen(model.getMen());
//                populationEntity.setWomen(model.getWomen());
//                populationEntity.setYearId(yearEntity.getYear());
//                populationEntity.setDistrictId(districtEntity.getDistrictId());
//                entityManager.persist(populationEntity);

            }

//            logger.info("All Cakes loaded into database from {}", cakesDownloadUrl);

    }

}


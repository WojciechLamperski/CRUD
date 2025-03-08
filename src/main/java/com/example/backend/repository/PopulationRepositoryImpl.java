package com.example.backend.repository;

import com.example.backend.entity.PopulationEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PopulationRepositoryImpl implements PopulationRepository {

    private Logger logger = LoggerFactory.getLogger(PopulationRepositoryImpl.class);

    private final EntityManager entityManager;

    // constructor injection
    public PopulationRepositoryImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public PopulationEntity save(PopulationEntity thePopulation) {
        try {
            logger.info("saving population into database");
            PopulationEntity dbyPopulation = entityManager.merge(thePopulation);
            return dbyPopulation;
        } catch (DataIntegrityViolationException e) {
            logger.info("DataIntegrityViolationException, while trying to save population into database");
            throw new DataIntegrityViolationException("Data integrity violation: Unable to save Population due to database constraints.");
        } catch (Exception e) {
            logger.info("Exception, while trying to save population into database");
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while saving the population. Please try again later.");
        }

    }

    @Override
    public PopulationEntity findById(int population_id) {
        try {
            logger.info("finding population by Id in database");
            return entityManager.find(PopulationEntity.class, population_id);
        } catch (NoResultException e) {
            logger.info("NoResultException, while trying to find population by Id in database");
            return null;
        } catch (Exception e) {
            logger.info("Exception, while trying to find population by Id in database");
            throw new RuntimeException("An error occurred while retrieving the population. Please try again later.");
        }
    }

    @Override
    public Page<PopulationEntity> findAll(Pageable pageable, Sort sort) {

        logger.info("finding all populations in database");

        String jpql = "SELECT p FROM PopulationEntity p";

        // Sorting
        if (sort != null && sort.isSorted()) {
            logger.info("sorting to find all populations in database");
            String orderBy = sort.get().map(order -> "p." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }
        try {
            TypedQuery<PopulationEntity> query = entityManager.createQuery(jpql, PopulationEntity.class);

            int totalRows = query.getResultList().size();
            List<PopulationEntity> populations = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(populations, pageable, totalRows);
        } catch (Error e){
            logger.info("Error, while trying to find all populations in database");
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public Page<PopulationEntity> findAllInVoivodeship(Pageable pageable, Sort sort, int voivodeshipId) {

        logger.info("finding all populations in specified voivodeship in database");

        String jpql = "SELECT p FROM PopulationEntity p WHERE p.district.voivodeship.voivodeshipId = :voivodeshipId";

        // Sorting
        if (sort != null && sort.isSorted()) {
            logger.info("sorting to find  all populations in specified voivodeship in database");
            String orderBy = sort.get().map(order -> "p." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }
        try {
            TypedQuery<PopulationEntity> query = entityManager.createQuery(jpql, PopulationEntity.class);
            query.setParameter("voivodeshipId", voivodeshipId);

            int totalRows = query.getResultList().size();

            List<PopulationEntity> populations = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(populations, pageable, totalRows);
        } catch (Error e){
            logger.info("Error, while trying to find  all populations in specified voivodeship in database");
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public Page<PopulationEntity> findAllInDistrict(Pageable pageable, Sort sort, int districtId) {

        logger.info("finding all populations in specified district in database");

        String jpql = "SELECT p FROM PopulationEntity p WHERE p.district.id = :districtId";

        // Sorting
        if (sort != null && sort.isSorted()) {
            logger.info("sorting to find  all populations in specified district in database");
            String orderBy = sort.get().map(order -> "p." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;  // ✅ Now, ORDER BY is part of the query BEFORE execution
        }
        try {
            TypedQuery<PopulationEntity> query = entityManager.createQuery(jpql, PopulationEntity.class);
            query.setParameter("districtId", districtId);

            int totalRows = query.getResultList().size();
            List<PopulationEntity> populations = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(populations, pageable, totalRows);
        } catch (Error e){
            logger.info("Error, while trying to find  all populations in specified district in database");
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public Page<PopulationEntity> findAllInYear(Pageable pageable, Sort sort, int yearId) {

        logger.info("finding all populations in specified year in database");

        String jpql = "SELECT p FROM PopulationEntity p WHERE p.year.id = :yearId";

        // Sorting
        if (sort != null && sort.isSorted()) {
            logger.info("sorting to find  all populations in specified year in database");
            String orderBy = sort.get().map(order -> "p." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }
        try {
            TypedQuery<PopulationEntity> query = entityManager.createQuery(jpql, PopulationEntity.class);
            query.setParameter("yearId", yearId);

            int totalRows = query.getResultList().size();
            List<PopulationEntity> populations = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(populations, pageable, totalRows);
        } catch (Error e){
            logger.info("Error, while trying to find  all populations in specified year in database");
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public String delete(int population_id) {
        try{
            logger.info("deleting population from database");
            PopulationEntity population = entityManager.find(PopulationEntity.class, population_id);
            entityManager.remove(population);
            return "Population successfully deleted"; // Indicating success
        } catch (DataIntegrityViolationException e) {
            logger.info("DataIntegrityViolationException, while trying to delete population from database");
            throw new DataIntegrityViolationException("Data integrity violation: Unable to delete population due to database constraints.");
        } catch (Exception e) {
            logger.info("Exception, while trying to delete population from database");
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while deleting the population. Please try again later.");
        }
    }
}

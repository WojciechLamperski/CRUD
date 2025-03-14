package com.example.backend.repository;

import com.example.backend.entity.DistrictEntity;
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

public class DistrictRepositoryImpl implements DistrictCustomRepository {

    private Logger logger = LoggerFactory.getLogger(DistrictRepositoryImpl.class);

    private final EntityManager entityManager;

    // constructor injection
    public DistrictRepositoryImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    public DistrictEntity save(DistrictEntity theDistrict) {
        try {
            logger.info("saving district into database");
            DistrictEntity dbDistrict = entityManager.merge(theDistrict);
            return dbDistrict;
        } catch (DataIntegrityViolationException e) {
            logger.info("DataIntegrityViolationException, while trying to save district into database");
            throw new DataIntegrityViolationException("Data integrity violation: Unable to save District due to database constraints.");
        } catch (Exception e) {
            logger.info("Exception, while trying to save district into database");
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while saving the district. Please try again later.");
        }
    }

    public DistrictEntity findById(int district_id) {
        try {
            logger.info("finding district by Id in database");
            return entityManager.find(DistrictEntity.class, district_id);
        } catch (NoResultException e) {
            logger.info("NoResultException, while trying to find district by Id in database");
            return null; // Return null if district not found
        } catch (Exception e) {
            logger.info("Exception, while trying to find district by Id in database");
            throw new RuntimeException("An error occurred while retrieving the district. Please try again later.");
        }
    }

    @Override
    public Page<DistrictEntity> findAll(Pageable pageable, Sort sort) {

        logger.info("finding all districts in database");

        String jpql = "SELECT d FROM DistrictEntity d";

        // Sorting
        if (sort != null && sort.isSorted()) {
            logger.info("sorting to find all districts in database");
            String orderBy = sort.get().map(order -> "d." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }

        try {
            TypedQuery<DistrictEntity> query = entityManager.createQuery(jpql, DistrictEntity.class);

            int totalRows = query.getResultList().size();
            List<DistrictEntity> districts = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(districts, pageable, totalRows);

        } catch (Error e){
            logger.info("Error, while trying to find all districts in database");
            throw new RuntimeException("An error occurred while retrieving the districts. Please try again later.");
        }

    }

    @Override
    public Page<DistrictEntity> findAllInVoivodeship(Pageable pageable, Sort sort, int voivodeshipId) {
        logger.info("finding all districts in specified voivodeship in database");
        String jpql = "SELECT d FROM DistrictEntity d WHERE d.voivodeship.id = :voivodeshipId";


        // Sorting
        if (sort != null && sort.isSorted()) {
            logger.info("sorting to find  all districts in specified voivodeship in database");
            String orderBy = sort.get().map(order -> "d." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }

        try {
            TypedQuery<DistrictEntity> query = entityManager.createQuery(jpql, DistrictEntity.class);
            query.setParameter("voivodeshipId", voivodeshipId);

            int totalRows = query.getResultList().size();
            List<DistrictEntity> districts = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(districts, pageable, totalRows);

        } catch (Error e){
            logger.info("Error, while trying to find  all districts in specified voivodeship in database");
            throw new RuntimeException("An error occurred while retrieving the districts. Please try again later.");
        }
    }

    public String delete(int district_id) {
        try{
            logger.info("deleting district from database");
            // Nullify the insertable = false, updatable = false constraints in District entity
            entityManager.createNativeQuery("UPDATE populations SET district_id = NULL WHERE district_id = ?1")
                    .setParameter(1, district_id)  // Positional parameter (index starts from 1)
                    .executeUpdate();

            // Now fetch and remove the voivodeship
            DistrictEntity district = entityManager.find(DistrictEntity.class, district_id);
            entityManager.remove(district);
            return "District successfully deleted";

        } catch (DataIntegrityViolationException e) {
            logger.info("DataIntegrityViolationException, while trying to delete district from database");
            throw new DataIntegrityViolationException("Data integrity violation: Unable to delete District due to database constraints.");
        } catch (Exception e) {
            logger.info("Exception, while trying to delete district from database");
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while deleting the district. Please try again later.");
        }
    }
}

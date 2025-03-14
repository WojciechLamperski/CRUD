package com.example.backend.repository;

import com.example.backend.entity.YearEntity;
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
import java.util.List;

public class YearRepositoryImpl implements YearCustomRepository {

    private Logger logger = LoggerFactory.getLogger(YearRepositoryImpl.class);

    private final EntityManager entityManager;

    // constructor injection
    public YearRepositoryImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    public YearEntity save(YearEntity theYear) {
        try {
            logger.info("saving year into database");
            YearEntity dbyYear = entityManager.merge(theYear);
            return dbyYear;
        } catch (DataIntegrityViolationException e) {
            logger.info("DataIntegrityViolationException, while trying to save year into database");
            throw new DataIntegrityViolationException("Data integrity violation: Unable to save Year due to database constraints.");
        } catch (Exception e) {
            logger.info("Exception, while trying to save year into database");
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while saving the year. Please try again later.");
        }
    }

    public YearEntity findById(int year_id) {
        try {
            logger.info("finding year by Id in database");
            return entityManager.find(YearEntity.class, year_id);
        } catch (NoResultException e) {
            logger.info("NoResultException, while trying to find year by Id in database");
            return null; // Return null if year not found
        } catch (Exception e) {
            logger.info("Exception, while trying to find year by Id in database");
            throw new RuntimeException("An error occurred while retrieving the year. Please try again later.");
        }
    }

    @Override
    public Page<YearEntity> findAll(Pageable pageable, Sort sort) {

        logger.info("finding all years in database");

        String jpql = "SELECT y FROM YearEntity y";

        // Sorting
        if (sort != null && sort.isSorted()) {
            logger.info("sorting to find all years in database");
            String orderBy = sort.get().map(order -> "y." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }

        try {
            TypedQuery<YearEntity> query = entityManager.createQuery(jpql, YearEntity.class);

            int totalRows = query.getResultList().size();
            List<YearEntity> year = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(year, pageable, totalRows);

        } catch (Error e){
            logger.info("Error, while trying to find all years in database");
            throw new RuntimeException("An error occurred while retrieving the years. Please try again later." + e.getMessage());
        }
    }

    public String delete(int year_id) {
        try{
            logger.info("deleting years from database");
            // Nullify the insertable = false, updatable = false constraints in District entity
            entityManager.createNativeQuery("UPDATE populations SET year_id = NULL WHERE year_id = ?1")
                    .setParameter(1, year_id)  // Positional parameter (index starts from 1)
                    .executeUpdate();

            // Now fetch and remove the voivodeship
            YearEntity year = entityManager.find(YearEntity.class, year_id);
            entityManager.remove(year);
            return "Year successfully deleted";

        } catch (DataIntegrityViolationException e) {
            logger.info("DataIntegrityViolationException, while trying to delete years from database");
            throw new DataIntegrityViolationException("Data integrity violation: Unable to delete Year due to database constraints.");
        } catch (Exception e) {
            logger.info("Exception, while trying to delete years from database");
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while deleting the year. Please try again later." + e.getMessage());
        }
    }
}
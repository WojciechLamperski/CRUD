package com.example.backend.DAO;

import com.example.backend.POJO.Voivodeship;
import com.example.backend.POJO.Year;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class YearDAOImpl implements YearDAO {

    private EntityManager entityManager;

    // constructor injection
    public YearDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public String save(Year theYear) {
        try {
            Year dbyYear = entityManager.merge(theYear);
            return ("object with id:" + dbyYear.getYearId() + " saved successfully");
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation: Unable to save Year due to database constraints.");
        } catch (Exception e) {
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while saving the year. Please try again later.");
        }
    }

    @Override
    public Year findById(int year_id) {
        try {
            return entityManager.find(Year.class, year_id);
        } catch (NoResultException e) {
            return null; // Return null if year not found
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the year. Please try again later.");
        }
    }

    @Override
    public Page<Year> findAll(Pageable pageable, Sort sort) {
        String jpql = "SELECT y FROM Year y";

        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "y." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;  // ✅ Now, ORDER BY is part of the query BEFORE execution
        }

        try {
            TypedQuery<Year> query = entityManager.createQuery(jpql, Year.class);

            int totalRows = query.getResultList().size();
            List<Year> year = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(year, pageable, totalRows);

        } catch (Error e){
            throw new RuntimeException("An error occurred while retrieving the years. Please try again later.");
        }
    }

    @Override
    public String delete(int year_id) {
        try{

            // Nullify the insertable = false, updatable = false constraints in District POJO
            entityManager.createNativeQuery("UPDATE populations SET year_id = NULL WHERE year_id = ?1")
                    .setParameter(1, year_id)  // Positional parameter (index starts from 1)
                    .executeUpdate();

            // Now fetch and remove the voivodeship
            Year year = entityManager.find(Year.class, year_id);
            entityManager.remove(year);
            return "Year successfully deleted";

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation: Unable to delete Year due to database constraints.");
        } catch (Exception e) {
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while deleting the year. Please try again later." + e.getMessage());
        }
    }
}
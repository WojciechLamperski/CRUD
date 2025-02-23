package com.example.backend.DAO;

import com.example.backend.POJO.Population;
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
public class PopulationDAOImpl implements PopulationDAO {

    private final EntityManager entityManager;

    // constructor injection
    public PopulationDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public String save(Population thePopulation) {
        try {
            Population dbyPopulation = entityManager.merge(thePopulation);
            return ("object with id:" + dbyPopulation.getPopulationId() + " saved successfully");
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation: Unable to save Population due to database constraints.");
        } catch (Exception e) {
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while saving the population. Please try again later.");
        }

    }

    @Override
    public Population findById(int population_id) {
        try {
            return entityManager.find(Population.class, population_id);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the population. Please try again later.");
        }
    }

    @Override
    public Page<Population> findAll(Pageable pageable, Sort sort) {
        String jpql = "SELECT p FROM Population p";

        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "p." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }
        try {
            TypedQuery<Population> query = entityManager.createQuery(jpql, Population.class);

            int totalRows = query.getResultList().size();
            List<Population> populations = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(populations, pageable, totalRows);
        } catch (Error e){
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public Page<Population> findAllInVoivodeship(Pageable pageable, Sort sort, int voivodeshipId) {
        String jpql = "SELECT p FROM Population p WHERE p.district.voivodeship.voivodeshipId = :voivodeshipId";

        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "p." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }
        try {
            TypedQuery<Population> query = entityManager.createQuery(jpql, Population.class);
            query.setParameter("voivodeshipId", voivodeshipId);

            int totalRows = query.getResultList().size();

            List<Population> populations = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(populations, pageable, totalRows);
        } catch (Error e){
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public Page<Population> findAllInDistrict(Pageable pageable, Sort sort, int districtId) {
        String jpql = "SELECT p FROM Population p WHERE p.district.id = :districtId";

        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "p." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;  // ✅ Now, ORDER BY is part of the query BEFORE execution
        }
        try {
            TypedQuery<Population> query = entityManager.createQuery(jpql, Population.class);
            query.setParameter("districtId", districtId);

            int totalRows = query.getResultList().size();
            List<Population> populations = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(populations, pageable, totalRows);
        } catch (Error e){
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public Page<Population> findAllInYear(Pageable pageable, Sort sort, int yearId) {
        String jpql = "SELECT p FROM Population p WHERE p.year.id = :yearId";

        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "p." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }
        try {
            TypedQuery<Population> query = entityManager.createQuery(jpql, Population.class);
            query.setParameter("yearId", yearId);

            int totalRows = query.getResultList().size();
            List<Population> populations = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(populations, pageable, totalRows);
        } catch (Error e){
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public String delete(int population_id) {
        try{
            Population population = entityManager.find(Population.class, population_id);
            entityManager.remove(population);
            return "Population successfully deleted"; // Indicating success
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation: Unable to delete population due to database constraints.");
        } catch (Exception e) {
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while deleting the population. Please try again later.");
        }
    }
}

package com.example.backend.repository;

import com.example.backend.entity.PopulationEntity;
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
public class PopulationRepositoryImpl implements PopulationRepository {

    private final EntityManager entityManager;

    // constructor injection
    public PopulationRepositoryImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public String save(PopulationEntity thePopulation) {
        try {
            PopulationEntity dbyPopulation = entityManager.merge(thePopulation);
            return ("object with id:" + dbyPopulation.getPopulationId() + " saved successfully");
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation: Unable to save Population due to database constraints.");
        } catch (Exception e) {
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while saving the population. Please try again later.");
        }

    }

    @Override
    public PopulationEntity findById(int population_id) {
        try {
            return entityManager.find(PopulationEntity.class, population_id);
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the population. Please try again later.");
        }
    }

    @Override
    public Page<PopulationEntity> findAll(Pageable pageable, Sort sort) {
        String jpql = "SELECT p FROM PopulationEntity p";

        // Sorting
        if (sort != null && sort.isSorted()) {
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
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public Page<PopulationEntity> findAllInVoivodeship(Pageable pageable, Sort sort, int voivodeshipId) {
        String jpql = "SELECT p FROM PopulationEntity p WHERE p.district.voivodeship.voivodeshipId = :voivodeshipId";

        // Sorting
        if (sort != null && sort.isSorted()) {
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
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public Page<PopulationEntity> findAllInDistrict(Pageable pageable, Sort sort, int districtId) {
        String jpql = "SELECT p FROM PopulationEntity p WHERE p.district.id = :districtId";

        // Sorting
        if (sort != null && sort.isSorted()) {
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
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public Page<PopulationEntity> findAllInYear(Pageable pageable, Sort sort, int yearId) {
        String jpql = "SELECT p FROM PopulationEntity p WHERE p.year.id = :yearId";

        // Sorting
        if (sort != null && sort.isSorted()) {
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
            throw new RuntimeException("An error occurred while retrieving the populations. Please try again later.");
        }
    }

    @Override
    public String delete(int population_id) {
        try{
            PopulationEntity population = entityManager.find(PopulationEntity.class, population_id);
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

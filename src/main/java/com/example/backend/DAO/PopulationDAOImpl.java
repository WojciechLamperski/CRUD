package com.example.backend.DAO;

import com.example.backend.POJO.Population;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PopulationDAOImpl implements PopulationDAO {

    private EntityManager entityManager;

    // constructor injection
    public PopulationDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public String save(Population thePopulation) {
        Population dbyPopulation = entityManager.merge(thePopulation);
        return ("object with id:" + dbyPopulation.getPopulationId() + " saved successfully");
    }

    @Override
    public Population findById(int population_id) {
        return entityManager.find(Population.class, population_id);
    }

    @Override
    public List<Population> findAll() {
        String jpql = "SELECT p FROM Population p";
        return entityManager.createQuery(jpql, Population.class).getResultList();
    }

    @Override
    public Page<Population> findAll(Pageable pageable) {
        String jpql = "SELECT p FROM Population p";
        TypedQuery<Population> query = entityManager.createQuery(jpql, Population.class);

        int totalRows = query.getResultList().size();
        List<Population> populations = query
                .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                .setMaxResults(pageable.getPageSize()) // Limit for pagination
                .getResultList();

        return new PageImpl<>(populations, pageable, totalRows);
    }

    @Override
    public String delete(int population_id) {
        Population population = entityManager.find(Population.class, population_id);
        if (population != null) {
            entityManager.remove(population);
            return "Population successfully deleted"; // Indicating success
        }
        return "Couldn't delete this population";
    }

}

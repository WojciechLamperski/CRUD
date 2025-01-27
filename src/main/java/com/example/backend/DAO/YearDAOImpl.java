package com.example.backend.DAO;

import com.example.backend.POJO.Year;
import jakarta.persistence.EntityManager;
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
    public int save(Year theYear) {
        Year dbyYear = entityManager.merge(theYear);
        return dbyYear.getYearId();
    }

    @Override
    public Year findById(int year_id) {
        return entityManager.find(Year.class, year_id);
    }

    @Override
    public List<Year> findAll() {
        String jpql = "SELECT y FROM Year y";
        return entityManager.createQuery(jpql, Year.class).getResultList();
    }

    @Override
    public int update(Year theYear) {
        Year dbyYear = entityManager.merge(theYear);
        return dbyYear.getYearId();
    }

    @Override
    public int delete(int year_id) {
        Year year = entityManager.find(Year.class, year_id);
        if (year != null) {
            entityManager.remove(year);
            return 1; // Indicating success
        }
        return 0; // Indicating failure
    }
}
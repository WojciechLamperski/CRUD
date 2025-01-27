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
    public String save(Year theYear) {
        Year dbyYear = entityManager.merge(theYear);
        return ("object with id:" + dbyYear.getYearId() + " saved successfully");
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
    public String delete(int year_id) {
        Year year = entityManager.find(Year.class, year_id);
        if (year != null) {
            entityManager.remove(year);
            return "Year successfully deleted"; // Indicating success
        }
        return "Couldn't delete this year";
    }
}
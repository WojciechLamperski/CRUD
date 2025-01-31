package com.example.backend.DAO;

import com.example.backend.POJO.Year;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
    public Page<Year> findAll(Pageable pageable, Sort sort) {
        String jpql = "SELECT y FROM Year y";

        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "y." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;  // ✅ Now, ORDER BY is part of the query BEFORE execution
        }

        TypedQuery<Year> query = entityManager.createQuery(jpql, Year.class);

        int totalRows = query.getResultList().size();
        List<Year> year = query
                .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                .setMaxResults(pageable.getPageSize()) // Limit for pagination
                .getResultList();

        return new PageImpl<>(year, pageable, totalRows);
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
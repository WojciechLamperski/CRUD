package com.example.backend.DAO;

import com.example.backend.POJO.Voivodeship;
import com.example.backend.exception.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoivodeshipDAOImpl implements VoivodeshipDAO {

    private EntityManager entityManager;

    // constructor injection
    public  VoivodeshipDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public String save(Voivodeship theVoivodeship) {
        Voivodeship dbyVoivodeship = entityManager.merge(theVoivodeship);
        return ("object with id:" + dbyVoivodeship.getVoivodeshipId() + " saved successfully");
    }

    @Override
    public Voivodeship findById(int voivodeship_id) {
        return entityManager.find(Voivodeship.class, voivodeship_id);
    }

    @Override
    public List<Voivodeship> findAll() {
        String jpql = "SELECT v FROM Voivodeship v";
        return entityManager.createQuery(jpql, Voivodeship.class).getResultList();
    }

    @Override
    public Page<Voivodeship> findAll(Pageable pageable, Sort sort) {

        String jpql = "SELECT v FROM Voivodeship v";

        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "v." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;  // ✅ Now, ORDER BY is part of the query BEFORE execution
        }

        TypedQuery<Voivodeship> query = entityManager.createQuery(jpql, Voivodeship.class);

        int totalRows = query.getResultList().size();
        List<Voivodeship> voivodeship = query
                .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                .setMaxResults(pageable.getPageSize()) // Limit for pagination
                .getResultList();

        return new PageImpl<>(voivodeship, pageable, totalRows);
    }

    @Override
    public String delete(int voivodeship_id) {
        Voivodeship voivodeship = entityManager.find(Voivodeship.class, voivodeship_id);
        if (voivodeship == null) {
            throw new EntityNotFoundException("Voivodeship with this id not found");
        }
        entityManager.remove(voivodeship);
        return "Voivodeship successfully deleted";
    }
}

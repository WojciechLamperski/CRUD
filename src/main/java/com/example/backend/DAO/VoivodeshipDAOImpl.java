package com.example.backend.DAO;

import com.example.backend.POJO.Voivodeship;
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
public class VoivodeshipDAOImpl implements VoivodeshipDAO {

    private final EntityManager entityManager;

    // constructor injection
    public  VoivodeshipDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public String save(Voivodeship theVoivodeship) {
        try {
            Voivodeship dbyVoivodeship = entityManager.merge(theVoivodeship);
            return ("object with id:" + dbyVoivodeship.getVoivodeshipId() + " saved successfully");
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation: Unable to save Voivodeship due to database constraints.");
        } catch (Exception e) {
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while saving the voivodeship. Please try again later.");
        }
    }

    @Override
    public Voivodeship findById(int voivodeship_id) {
        try{
            return entityManager.find(Voivodeship.class, voivodeship_id);
        } catch (NoResultException e) {
            return null; // Return null if voivodeship not found
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the voivodeship. Please try again later.");
        }
    }

    @Override
    public Page<Voivodeship> findAll(Pageable pageable, Sort sort) {

        String jpql = "SELECT v FROM Voivodeship v";

        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "v." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }

        try {
            TypedQuery<Voivodeship> query = entityManager.createQuery(jpql, Voivodeship.class);

            int totalRows = query.getResultList().size();
            List<Voivodeship> voivodeship = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(voivodeship, pageable, totalRows);
        } catch (Error e){
            throw new RuntimeException("An error occurred while retrieving the voivodeships. Please try again later.");
        }
    }

    @Override
    public String delete(int voivodeship_id) {
        try{

            // Nullify the insertable = false, updatable = false constraints in District POJO
            entityManager.createNativeQuery("UPDATE districts SET voivodeship_id = NULL WHERE voivodeship_id = ?1")
                    .setParameter(1, voivodeship_id)  // Positional parameter (index starts from 1)
                    .executeUpdate();

            // Now fetch and remove the voivodeship
            Voivodeship voivodeship = entityManager.find(Voivodeship.class, voivodeship_id);
            entityManager.remove(voivodeship);
            return "Voivodeship successfully deleted";

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation: Unable to delete Voivodeship due to database constraints.");
        } catch (Exception e) {
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while deleting the voivodeship:" + e.getMessage(), e);
        }
    }
}

package com.example.backend.DAO;

import com.example.backend.POJO.District;
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
public class DistrictDAOImpl implements DistrictDAO {

    private EntityManager entityManager;

    // constructor injection
    public DistrictDAOImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public String save(District theDistrict) {
        try {
            District dbDistrict = entityManager.merge(theDistrict);
            return ("object with id:" + dbDistrict.getDistrictId() + " saved successfully");
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation: Unable to save District due to database constraints.");
        } catch (Exception e) {
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while saving the district. Please try again later.");
        }
    }

    @Override
    public District findById(int district_id) {
        try {
            return entityManager.find(District.class, district_id);
        } catch (NoResultException e) {
            return null; // Return null if district not found
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the district. Please try again later.");
        }
    }

    @Override
    public Page<District> findAll(Pageable pageable, Sort sort) {
        String jpql = "SELECT d FROM District d";

        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "d." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }

        try {
            TypedQuery<District> query = entityManager.createQuery(jpql, District.class);

            int totalRows = query.getResultList().size();
            List<District> districts = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(districts, pageable, totalRows);

        } catch (Error e){
            throw new RuntimeException("An error occurred while retrieving the districts. Please try again later.");
        }

    }

    @Override
    public Page<District> findAllInVoivodeship(Pageable pageable, Sort sort, int voivodeshipId) {
        String jpql = "SELECT d FROM District d WHERE d.voivodeship.id = :voivodeshipId";


        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "d." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;
        }

        try {
            TypedQuery<District> query = entityManager.createQuery(jpql, District.class);
            query.setParameter("voivodeshipId", voivodeshipId);

            int totalRows = query.getResultList().size();
            List<District> districts = query
                    .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                    .setMaxResults(pageable.getPageSize()) // Limit for pagination
                    .getResultList();

            return new PageImpl<>(districts, pageable, totalRows);

        } catch (Error e){
            throw new RuntimeException("An error occurred while retrieving the districts. Please try again later.");
        }
    }

    @Override
    public String delete(int district_id) {
        try{

            // Nullify the insertable = false, updatable = false constraints in District POJO
            entityManager.createNativeQuery("UPDATE populations SET district_id = NULL WHERE district_id = ?1")
                    .setParameter(1, district_id)  // Positional parameter (index starts from 1)
                    .executeUpdate();

            // Now fetch and remove the voivodeship
            District district = entityManager.find(District.class, district_id);
            entityManager.remove(district);
            return "District successfully deleted";

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation: Unable to delete District due to database constraints.");
        } catch (Exception e) {
            // Catch any other exceptions and provide a more generic error message
            throw new RuntimeException("An error occurred while deleting the district. Please try again later.");
        }
    }
}

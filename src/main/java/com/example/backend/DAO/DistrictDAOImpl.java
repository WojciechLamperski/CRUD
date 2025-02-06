package com.example.backend.DAO;

import com.example.backend.POJO.District;
import com.example.backend.exception.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
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
        District dbDistrict = entityManager.merge(theDistrict);
        return ("object with id:" + dbDistrict.getDistrictId() + " saved successfully");
    }

    @Override
    public District findById(int district_id) {
        String jpql = "SELECT d FROM District d JOIN FETCH d.voivodeship WHERE d.districtId = :district_id";
        try {
            return entityManager.createQuery(jpql, District.class)
                    .setParameter("district_id", district_id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if district not found
        }
    }

    @Override
    public List<District> findAll() {
        String jpql = "SELECT d FROM District d JOIN FETCH d.voivodeship";
        return entityManager.createQuery(jpql, District.class).getResultList();
    }

    @Override
    public Page<District> findAll(Pageable pageable, Sort sort) {
        String jpql = "SELECT d FROM District d";

        // Sorting
        if (sort != null && sort.isSorted()) {
            String orderBy = sort.get().map(order -> "d." + order.getProperty() + " " + order.getDirection())
                    .reduce((a, b) -> a + ", " + b).orElse("");
            jpql += " ORDER BY " + orderBy;  // ✅ Now, ORDER BY is part of the query BEFORE execution
        }

        TypedQuery<District> query = entityManager.createQuery(jpql, District.class);

        int totalRows = query.getResultList().size();
        List<District> districts = query
                .setFirstResult((int) pageable.getOffset()) // Offset for pagination
                .setMaxResults(pageable.getPageSize()) // Limit for pagination
                .getResultList();

        return new PageImpl<>(districts, pageable, totalRows);
    }

    @Override
    public String delete(int district_id) {
        District district = entityManager.find(District.class, district_id);
        if (district == null) {
            throw new EntityNotFoundException("Voivodeship with this id not found");
        }
        entityManager.remove(district);
        return "District successfully deleted"; // Indicating success
    }
}

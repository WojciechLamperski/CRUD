package com.example.backend.DAO;

import com.example.backend.POJO.District;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<District> findAll(Pageable pageable) {
        System.out.println(pageable);
        String jpql = "SELECT d FROM District d";
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
        if (district != null) {
            entityManager.remove(district);
            return "District successfully deleted"; // Indicating success
        }
        return "Couldn't delete this district";
    }
}

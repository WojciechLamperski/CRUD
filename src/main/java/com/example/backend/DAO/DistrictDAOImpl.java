package com.example.backend.DAO;

import com.example.backend.POJO.District;
import jakarta.persistence.EntityManager;
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
        District dbyDistrict = entityManager.merge(theDistrict);
        return ("object with id:" + dbyDistrict.getDistrictId() + " saved successfully");
    }

    @Override
    public District findById(int district_id) {
        return entityManager.find(District.class, district_id);
    }

    @Override
    public List<District> findAll() {
        String jpql = "SELECT d FROM District d";
        return entityManager.createQuery(jpql, District.class).getResultList();
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

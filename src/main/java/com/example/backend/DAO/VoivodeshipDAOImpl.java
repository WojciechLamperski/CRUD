package com.example.backend.DAO;

import com.example.backend.POJO.Voivodeship;
import jakarta.persistence.EntityManager;
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
    public String delete(int voivodeship_id) {
        Voivodeship voivodeship = entityManager.find(Voivodeship.class, voivodeship_id);
        if (voivodeship != null) {
            entityManager.remove(voivodeship);
            return "Voivodeship successfully deleted"; // Indicating success
        }
        return "Couldn't delete this voivodeship";
    }
}

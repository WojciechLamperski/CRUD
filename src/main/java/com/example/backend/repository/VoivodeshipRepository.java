package com.example.backend.repository;

import com.example.backend.entity.VoivodeshipEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoivodeshipRepository extends CrudRepository<VoivodeshipEntity, Integer>, VoivodeshipCustomRepository {

}
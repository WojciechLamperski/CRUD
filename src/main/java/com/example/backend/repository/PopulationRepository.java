package com.example.backend.repository;

import com.example.backend.entity.PopulationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PopulationRepository extends CrudRepository<PopulationEntity, Integer>, PopulationCustomRepository {
}

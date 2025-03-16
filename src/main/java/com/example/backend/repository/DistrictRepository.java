package com.example.backend.repository;

import com.example.backend.entity.DistrictEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DistrictRepository extends CrudRepository<DistrictEntity, Integer>, DistrictCustomRepository {
}

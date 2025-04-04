package com.example.backend.repository;

import com.example.backend.entity.YearEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface YearRepository extends CrudRepository<YearEntity, Integer>, YearCustomRepository {

}
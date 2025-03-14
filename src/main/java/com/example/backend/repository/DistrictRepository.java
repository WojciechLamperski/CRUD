package com.example.backend.repository;

import com.example.backend.entity.DistrictEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;


@Repository
public interface DistrictRepository extends CrudRepository<DistrictEntity, Integer>, DistrictCustomRepository {
}

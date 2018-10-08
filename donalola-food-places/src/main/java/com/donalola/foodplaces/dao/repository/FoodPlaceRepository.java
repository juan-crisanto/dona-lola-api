package com.donalola.foodplaces.dao.repository;

import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodPlaceRepository extends JpaRepository<FoodPlaceEntity, Long> {

    Page<FoodPlaceEntity> findAll(Pageable pageable);

    Page<FoodPlaceEntity> findAllByName(String name);

    Page<FoodPlaceEntity> findAllByDistrict(String district);
}

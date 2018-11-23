package com.donalola.foodplaces.dao.repository;

import com.donalola.foodplaces.dao.entity.FoodPlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodPlaceRepository extends JpaRepository<FoodPlaceEntity, Long> {

    List<FoodPlaceEntity> findAllByNameContaining(String name);


}

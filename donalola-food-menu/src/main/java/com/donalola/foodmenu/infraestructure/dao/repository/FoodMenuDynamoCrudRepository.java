package com.donalola.foodmenu.infraestructure.dao.repository;

import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuDynamoEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@EnableScan
public interface FoodMenuDynamoCrudRepository extends CrudRepository<FoodMenuDynamoEntity, String> {

    @Override
    Optional<FoodMenuDynamoEntity> findById(@Param("id") String s);

    List<FoodMenuDynamoEntity> findFoodMenuDynamoEntitiesByFoodPlaceId(String foodPlaceId);

    List<FoodMenuDynamoEntity> findAllByFoodPlaceIdAndAndCreatedDatetimeIsAfter(String foodPlaceId, LocalDateTime localDateTime);


}

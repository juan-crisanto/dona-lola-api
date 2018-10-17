package com.donalola.foodmenu.infraestructure.dao.repository;

import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuDynamoEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface FoodMenuDynamoCrudRepository extends CrudRepository<FoodMenuDynamoEntity, String> {

    List<FoodMenuDynamoEntity> findFoodMenuDynamoEntitiesByFoodPlaceId(String id);

}

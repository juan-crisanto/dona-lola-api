package com.donalola.foodmenu.infraestructure.dao.repository;

import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuItemDynamoEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface FoodMenuItemDynamoCrudRepository extends CrudRepository<FoodMenuItemDynamoEntity, String> {

    List<FoodMenuItemDynamoEntity> findAllByMenuId(String menuId);


}

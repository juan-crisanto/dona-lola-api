package com.donalola.foodmenu.infraestructure.dao.repository;

import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuItemDynamoEntity;
import org.springframework.data.repository.CrudRepository;

public interface FoodMenuItemDynamoCrudRepository extends CrudRepository<FoodMenuItemDynamoEntity, String> {
}

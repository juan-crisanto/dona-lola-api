package com.donalola.foodmenu.dao.repository;

import com.donalola.foodmenu.dao.entity.FoodMenuEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface FoodMenuRepository extends CrudRepository<FoodMenuEntity, String> {
}

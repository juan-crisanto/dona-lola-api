package com.donalola.orders.infraestructure.dao.repository;

import com.donalola.orders.infraestructure.dao.entity.OrderDynamoEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface OrderDynamoCrudRepository extends CrudRepository<OrderDynamoEntity, String> {
}
package com.donalola.orders.infraestructure.dao.repository;

import com.donalola.orders.infraestructure.dao.entity.OrderDynamoEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@EnableScan
public interface OrderDynamoCrudRepository extends CrudRepository<OrderDynamoEntity, String> {

    @Override
    Optional<OrderDynamoEntity> findById(@Param("id") String s);

    List<OrderDynamoEntity> findAllByFoodPlaceIdAndCreatedDatetimeAfter(String foodPlaceId, LocalDateTime localDateTime);

    List<OrderDynamoEntity> findAllByCustomerId(String customerId);

    List<OrderDynamoEntity> findAllByCustomerIdAndStatus(String customerId, String status);

    List<OrderDynamoEntity> findAllByFoodPlaceIdAndCreatedDatetimeAfterAndStatus(String foodPlaceId, LocalDateTime localDateTime, String status);

}
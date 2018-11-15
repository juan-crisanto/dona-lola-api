package com.donalola.customer.infraestructure.dao;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@EnableScan
public interface CustomerDynamoCrudRepository extends CrudRepository<CustomerDynamoEntity, String> {

    @Override
    Optional<CustomerDynamoEntity> findById(@Param("id") String s);
}

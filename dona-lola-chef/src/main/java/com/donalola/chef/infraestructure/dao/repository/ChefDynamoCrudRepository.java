package com.donalola.chef.infraestructure.dao.repository;

import com.donalola.chef.infraestructure.dao.entity.ChefDynamoEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface ChefDynamoCrudRepository extends CrudRepository<ChefDynamoEntity, String> {

    @Override
    Optional<ChefDynamoEntity> findById(@Param("id") String s);

    List<ChefDynamoEntity> findAllByUserId(String userId);

    List<ChefDynamoEntity> findAllByNameContaining(String name);
}

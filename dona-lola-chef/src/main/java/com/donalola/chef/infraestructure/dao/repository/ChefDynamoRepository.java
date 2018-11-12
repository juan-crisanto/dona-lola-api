package com.donalola.chef.infraestructure.dao.repository;

import com.donalola.ChefID;
import com.donalola.chef.domain.Chef;
import com.donalola.chef.domain.ChefRepository;
import com.donalola.chef.infraestructure.dao.entity.ChefDynamoEntity;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ChefDynamoRepository implements ChefRepository {

    private final ChefDynamoCrudRepository chefDynamoCrudRepository;

    public ChefDynamoRepository(ChefDynamoCrudRepository chefDynamoCrudRepository) {
        this.chefDynamoCrudRepository = chefDynamoCrudRepository;
    }

    @Override
    public Chef get(ChefID chefID) {
        Optional<ChefDynamoEntity> entity = this.chefDynamoCrudRepository.findById(chefID.toString());
        if (!entity.isPresent()) {
            throw new IllegalArgumentException("No se ha encontrado chef con el ID " + chefID.toString());
        }
        return entity.get().convert();
    }

    @Override
    public Chef save(Chef chef) {
        ChefDynamoEntity savedEntity = this.chefDynamoCrudRepository.save(ChefDynamoEntity.of(chef));
        return savedEntity.convert();
    }

    @Override
    public Chef update(Chef chef) {
        get(chef.getId());
        ChefDynamoEntity savedEntity = this.chefDynamoCrudRepository.save(ChefDynamoEntity.of(chef));
        return savedEntity.convert();
    }

    @Override
    public Chef getByUser(String userId) {
        List<ChefDynamoEntity> entityList = this.chefDynamoCrudRepository.findAllByUserId(userId);
        Optional<ChefDynamoEntity> entity = entityList.parallelStream().findFirst();
        if (!entity.isPresent()) {
            throw new IllegalArgumentException("No se ha creado ningún chef para esta cuenta");
        }
        return entity.get().convert();
    }

    @Override
    public List<Chef> findByName(String name) {
        return null;
    }

    @Override
    public List<Chef> findAll() {
        Iterable<ChefDynamoEntity> allChefs = this.chefDynamoCrudRepository.findAll();
        if (IterableUtils.isEmpty(allChefs)) {
            return Collections.emptyList();
        }
        List<Chef> chefList = new ArrayList<>(IterableUtils.size(allChefs));
        allChefs.forEach(chefDynamoEntity -> {
            chefList.add(chefDynamoEntity.convert());
        });
        return chefList;
    }
}

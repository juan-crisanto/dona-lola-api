package com.donalola.foodmenu.infraestructure.dao.repository;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.FoodMenus;
import com.donalola.foodmenu.JustToIterateFoodMenus;
import com.donalola.foodmenu.domain.dao.repository.FoodMenuRepository;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuDynamoEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class FoodMenuDynamoRepository implements FoodMenuRepository, FoodMenus {

    private final FoodMenuDynamoCrudRepository foodMenuDynamoCrudRepository;
    private final FoodMenuFactory<FoodMenuDynamoEntity> foodMenuFactory;

    public FoodMenuDynamoRepository(FoodMenuDynamoCrudRepository foodMenuDynamoCrudRepository, FoodMenuFactory<FoodMenuDynamoEntity> foodMenuFactory) {
        this.foodMenuDynamoCrudRepository = foodMenuDynamoCrudRepository;
        this.foodMenuFactory = foodMenuFactory;
    }

    @Override
    public Iterator<FoodMenu> iterator() {
        Iterable<FoodMenuDynamoEntity> iterableOfAll = this.foodMenuDynamoCrudRepository.findAll();
        List<FoodMenu> foodMenuList = new ArrayList<>(CollectionUtils.size(iterableOfAll));
        iterableOfAll.forEach(foodMenuDynamoEntity -> foodMenuList.add(this.foodMenuFactory.create(foodMenuDynamoEntity)));
        return foodMenuList.iterator();
    }

    @Override
    public FoodMenu add(FoodMenu foodMenu) {
        if (!foodMenu.hasAnyItem()) {
            throw new IllegalArgumentException("Es necesario especificar los Items del menú");
        }
        FoodMenuDynamoEntity foodMenuDynamoEntity = this.foodMenuFactory.create(foodMenu);
        FoodMenuDynamoEntity savedEntity = this.foodMenuDynamoCrudRepository.save(foodMenuDynamoEntity);
        return this.foodMenuFactory.create(savedEntity);
    }

    @Override
    public FoodMenus listByFoodPlace(String idFoodPlace) {
        List<FoodMenuDynamoEntity> entityList = this.foodMenuDynamoCrudRepository.findFoodMenuDynamoEntitiesByFoodPlaceId(idFoodPlace);
        List<FoodMenu> foodMenuList = new ArrayList<>(CollectionUtils.size(entityList));
        entityList.stream().forEach(foodMenuDynamoEntity -> foodMenuList.add(this.foodMenuFactory.create(foodMenuDynamoEntity)));
        return new JustToIterateFoodMenus(foodMenuList.iterator());
    }

    @Override
    public FoodMenus listByDate(LocalDate localDate) {
        return null;
    }

}

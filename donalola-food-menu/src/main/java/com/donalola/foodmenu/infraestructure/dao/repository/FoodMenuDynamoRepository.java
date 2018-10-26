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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
    public FoodMenu get(String menuId) {
        Optional<FoodMenuDynamoEntity> entity = this.foodMenuDynamoCrudRepository.findById(menuId);
        if (!entity.isPresent()) {
            throw new IllegalArgumentException("Menu id no válido : " + menuId);
        }
        return this.foodMenuFactory.create(entity.get());
    }

    @Override
    public FoodMenu addMenuWithItems(FoodMenu foodMenu) {
        if (!foodMenu.hasAnyItem()) {
            throw new IllegalArgumentException("Es necesario especificar los Items del menú");
        }
        setItemIds(foodMenu);
        FoodMenuDynamoEntity foodMenuDynamoEntity = this.foodMenuFactory.create(foodMenu);
        FoodMenuDynamoEntity savedEntity = this.foodMenuDynamoCrudRepository.save(foodMenuDynamoEntity);
        return this.foodMenuFactory.create(savedEntity);
    }

    @Override
    public FoodMenu addItemsToMenu(String menuId, List<FoodMenu.Item> itemList) {
        FoodMenu foodMenu = this.get(menuId);
        foodMenu.getItems().addAll(setItemIds(itemList));
        FoodMenuDynamoEntity savedEntity = this.foodMenuDynamoCrudRepository.save(this.foodMenuFactory.create(foodMenu));
        return this.foodMenuFactory.create(savedEntity);
    }

    private void setItemIds(FoodMenu foodMenu) {
        foodMenu.setItems(setItemIds(foodMenu.getItems()));
    }

    private List<FoodMenu.Item> setItemIds(List<FoodMenu.Item> itemList) {
        List<FoodMenu.Item> withIdItems = new ArrayList<>(CollectionUtils.size(itemList));
        for (FoodMenu.Item item : itemList) {
            item.setId(UUID.randomUUID().toString());
            withIdItems.add(item);
        }
        return withIdItems;
    }

    @Override
    public FoodMenus listByFoodPlace(String idFoodPlace) {
        List<FoodMenuDynamoEntity> entityList = this.foodMenuDynamoCrudRepository.findFoodMenuDynamoEntitiesByFoodPlaceId(idFoodPlace);
        List<FoodMenu> foodMenuList = new ArrayList<>(CollectionUtils.size(entityList));
        entityList.stream().forEach(foodMenuDynamoEntity -> foodMenuList.add(this.foodMenuFactory.create(foodMenuDynamoEntity)));
        return new JustToIterateFoodMenus(foodMenuList.iterator());
    }

    @Override
    public FoodMenus listTodayFoodPlaceMenus(String foodPlaceId) {
        LocalDateTime todayInitTime = LocalDate.now().atTime(LocalTime.MIN);
        if (log.isDebugEnabled()) {
            log.debug(String.format("Getting menus of %s after %s", foodPlaceId, todayInitTime.toString()));
        }
        List<FoodMenuDynamoEntity> entityList = this.foodMenuDynamoCrudRepository.findAllByFoodPlaceIdAndAndCreatedDatetimeIsAfter(foodPlaceId, todayInitTime);
        List<FoodMenu> foodMenuList = new ArrayList<>(CollectionUtils.size(entityList));
        for (final FoodMenuDynamoEntity entity : entityList) {
            FoodMenu foodMenu = this.foodMenuFactory.create(entity);
            if (Optional.ofNullable(foodMenu).isPresent()) {
                foodMenuList.add(foodMenu);
            }
        }
        return new JustToIterateFoodMenus(foodMenuList.iterator());
    }

    @Override
    public FoodMenus listByDate(LocalDate localDate) {
        return null;
    }

}

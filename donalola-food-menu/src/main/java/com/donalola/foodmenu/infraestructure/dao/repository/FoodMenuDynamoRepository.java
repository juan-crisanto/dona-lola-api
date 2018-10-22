package com.donalola.foodmenu.infraestructure.dao.repository;

import com.donalola.foodmenu.*;
import com.donalola.foodmenu.domain.dao.repository.FoodMenuRepository;
import com.donalola.foodmenu.domain.dao.repository.ItemMenuRepository;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuDynamoEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class FoodMenuDynamoRepository implements FoodMenuRepository, FoodMenus {

    private final FoodMenuDynamoCrudRepository foodMenuDynamoCrudRepository;
    private final FoodMenuFactory<FoodMenuDynamoEntity> foodMenuFactory;
    private final ItemMenuRepository itemMenuRepository;
    private final ItemsMenu itemMenus;

    public FoodMenuDynamoRepository(FoodMenuDynamoCrudRepository foodMenuDynamoCrudRepository, FoodMenuFactory<FoodMenuDynamoEntity> foodMenuFactory, ItemMenuRepository itemMenuRepository, ItemsMenu itemMenus) {
        this.foodMenuDynamoCrudRepository = foodMenuDynamoCrudRepository;
        this.foodMenuFactory = foodMenuFactory;
        this.itemMenuRepository = itemMenuRepository;
        this.itemMenus = itemMenus;
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
        FoodMenu foodMenu = this.foodMenuFactory.create(entity.get());
        retrieveItems(foodMenu);
        return foodMenu;
    }

    @Override
    public FoodMenu addMenuWithItems(FoodMenu foodMenu) {
        if (!foodMenu.hasAnyItem()) {
            throw new IllegalArgumentException("Es necesario especificar los Items del menú");
        }
        FoodMenuDynamoEntity foodMenuDynamoEntity = this.foodMenuFactory.create(foodMenu);
        FoodMenuDynamoEntity savedEntity = this.foodMenuDynamoCrudRepository.save(foodMenuDynamoEntity);
        FoodMenu savedFoodMenu = this.foodMenuFactory.create(savedEntity);
        saveItemsAndComplete(savedFoodMenu, foodMenu.getItems());
        return savedFoodMenu;
    }

    @Override
    public FoodMenu addItemsToMenu(FoodMenu foodMenu){
        saveItemsAndComplete(foodMenu, foodMenu.getItems());
        return foodMenu;
    }

    private void saveItemsAndComplete(FoodMenu savedFoodMenu, final List<ItemMenu> itemMenuList) {
        List<ItemMenu> addedItemMenuList = new ArrayList<>(CollectionUtils.size(itemMenuList));
        for (ItemMenu itemMenu : itemMenuList) {
            itemMenu.setMenuId(savedFoodMenu.getId());
            addedItemMenuList.add(this.itemMenuRepository.add(itemMenu));
        }
        savedFoodMenu.setItems(addedItemMenuList);
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
                retrieveItems(foodMenu);
                foodMenuList.add(foodMenu);
            }
        }
        return new JustToIterateFoodMenus(foodMenuList.iterator());
    }

    private void retrieveItems(FoodMenu foodMenu) {
        foodMenu.setItems(IterableUtils.toList(this.itemMenus.getByMenu(foodMenu.getId())));
    }

    @Override
    public FoodMenus listByDate(LocalDate localDate) {
        return null;
    }

}

package com.donalola.foodmenu.infraestructure.dao.repository;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.FoodMenus;
import com.donalola.foodmenu.ItemMenu;
import com.donalola.foodmenu.JustToIterateFoodMenus;
import com.donalola.foodmenu.domain.dao.repository.FoodMenuRepository;
import com.donalola.foodmenu.domain.dao.repository.ItemMenuRepository;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuDynamoEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class FoodMenuDynamoRepository implements FoodMenuRepository, FoodMenus {

    private final FoodMenuDynamoCrudRepository foodMenuDynamoCrudRepository;
    private final FoodMenuFactory<FoodMenuDynamoEntity> foodMenuFactory;
    private final ItemMenuRepository itemMenuRepository;

    public FoodMenuDynamoRepository(FoodMenuDynamoCrudRepository foodMenuDynamoCrudRepository, FoodMenuFactory<FoodMenuDynamoEntity> foodMenuFactory, ItemMenuRepository itemMenuRepository) {
        this.foodMenuDynamoCrudRepository = foodMenuDynamoCrudRepository;
        this.foodMenuFactory = foodMenuFactory;
        this.itemMenuRepository = itemMenuRepository;
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
        FoodMenu savedFoodMenu = this.foodMenuFactory.create(savedEntity);
        saveItemsAndComplete(savedFoodMenu, foodMenu.getItems());
        return savedFoodMenu;
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
        entityList.stream().forEach(foodMenuDynamoEntity -> foodMenuList.add(this.foodMenuFactory.create(foodMenuDynamoEntity)));
        return new JustToIterateFoodMenus(foodMenuList.iterator());
    }

    @Override
    public FoodMenus listByDate(LocalDate localDate) {
        return null;
    }

}

package com.donalola.foodmenu.domain;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.FoodMenus;
import com.donalola.foodmenu.domain.dao.repository.FoodMenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FoodMenuRepositoryManager implements FoodMenuManager {

    private final FoodMenuRepository foodMenuRepository;
    private final FoodMenus foodMenus;

    public FoodMenuRepositoryManager(FoodMenuRepository foodMenuRepository, FoodMenus foodMenus) {
        this.foodMenuRepository = foodMenuRepository;
        this.foodMenus = foodMenus;
    }

    @Override
    public FoodMenu addMenuWithItems(FoodMenu foodMenu) {
        return this.foodMenuRepository.addMenuWithItems(foodMenu);
    }

    @Override
    public FoodMenu addItemsToMenu(String foodMenuId, List<FoodMenu.Item> itemList) {
        return this.foodMenuRepository.addItemsToMenu(foodMenuId, itemList);
    }

    @Override
    public FoodMenu get(String menuId) {
        return this.foodMenuRepository.get(menuId);
    }

    @Override
    public FoodMenus getByFoodPlace(String foodPlaceId) {
        return this.foodMenus.listByFoodPlace(foodPlaceId);
    }

    @Override
    public FoodMenus listTodayFoodPlaceMenus(String foodPlaceId) {
        return this.foodMenus.listTodayFoodPlaceMenus(foodPlaceId);
    }
}

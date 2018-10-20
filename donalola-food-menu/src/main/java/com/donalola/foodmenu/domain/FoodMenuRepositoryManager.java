package com.donalola.foodmenu.domain;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.FoodMenus;
import com.donalola.foodmenu.domain.dao.repository.FoodMenuRepository;
import org.springframework.stereotype.Component;

@Component
public class FoodMenuRepositoryManager implements FoodMenuManager {

    private final FoodMenuRepository foodMenuRepository;
    private final FoodMenus foodMenus;

    public FoodMenuRepositoryManager(FoodMenuRepository foodMenuRepository, FoodMenus foodMenus) {
        this.foodMenuRepository = foodMenuRepository;
        this.foodMenus = foodMenus;
    }

    @Override
    public FoodMenu add(FoodMenu foodMenu) {
        return this.foodMenuRepository.add(foodMenu);
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

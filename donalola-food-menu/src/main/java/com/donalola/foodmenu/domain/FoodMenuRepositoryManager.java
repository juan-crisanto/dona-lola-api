package com.donalola.foodmenu.domain;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.FoodMenus;
import com.donalola.foodmenu.domain.dao.repository.FoodMenuRepository;
import com.donalola.foodmenu.domain.service.FoodMenuService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FoodMenuRepositoryManager implements FoodMenuManager {

    private final FoodMenuRepository foodMenuRepository;
    private final FoodMenus foodMenus;
    private final FoodMenuService foodMenuService;

    public FoodMenuRepositoryManager(FoodMenuRepository foodMenuRepository, FoodMenus foodMenus, FoodMenuService foodMenuService) {
        this.foodMenuRepository = foodMenuRepository;
        this.foodMenus = foodMenus;
        this.foodMenuService = foodMenuService;
    }


    @Override
    public FoodMenu addMenuWithItems(FoodMenu foodMenu) {
        return this.foodMenuRepository.addMenuWithItems(foodMenu);
    }

    @Override
    public FoodMenu sellItem(String foodMenuId, String itemId, Integer quantity) {
        return this.foodMenuService.sellItem(foodMenuId, itemId, quantity);
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

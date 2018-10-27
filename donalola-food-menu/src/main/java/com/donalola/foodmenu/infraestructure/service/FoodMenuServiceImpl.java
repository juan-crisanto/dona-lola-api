package com.donalola.foodmenu.infraestructure.service;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.dao.repository.FoodMenuRepository;
import com.donalola.foodmenu.domain.service.FoodMenuService;
import org.springframework.stereotype.Service;

@Service
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuRepository foodMenuRepository;

    public FoodMenuServiceImpl(FoodMenuRepository foodMenuRepository) {
        this.foodMenuRepository = foodMenuRepository;
    }

    @Override
    public FoodMenu sellItem(String foodMenuId, String itemId, Integer quantity) {
        FoodMenu foodMenu = this.foodMenuRepository.get(foodMenuId);
        foodMenu.takeItem(itemId, quantity);
        this.foodMenuRepository.update(foodMenu);
        return foodMenu;
    }
}

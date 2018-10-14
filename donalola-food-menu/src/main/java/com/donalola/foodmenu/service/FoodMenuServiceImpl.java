package com.donalola.foodmenu.service;

import com.donalola.foodmenu.dao.repository.FoodMenuRepository;
import org.springframework.stereotype.Service;

@Service
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuRepository foodMenuRepository;

    public FoodMenuServiceImpl(FoodMenuRepository foodMenuRepository) {
        this.foodMenuRepository = foodMenuRepository;
    }

    @Override
    public boolean supports(Class<?> supportedClass) {
        return false;
    }
}

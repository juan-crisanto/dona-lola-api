package com.donalola.foodmenu.infraestructure.dao.factory;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import com.donalola.foodmenu.infraestructure.dao.entity.FoodMenuDynamoEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class FromDynamoEntityFoodMenuFactoryTest {

    private FoodMenuFactory<FoodMenuDynamoEntity> foodMenuFactory;

    @Before
    public void setUp() throws Exception {
        this.foodMenuFactory = new FromDynamoEntityFoodMenuFactory();
    }

    @Test
    public void createDomain() {
        FoodMenuDynamoEntity foodMenuDynamoEntity = new FoodMenuDynamoEntity();
        foodMenuDynamoEntity.setCreatedDatetime(LocalDateTime.now());
        foodMenuDynamoEntity.setFoodPlaceId("1");
        foodMenuDynamoEntity.setFoodPlaceName("Restaurante de Pedro");
        foodMenuDynamoEntity.setId("aabads-dar3rrfsf-afasf2");
        foodMenuDynamoEntity.setName("Menú Regular");
        foodMenuDynamoEntity.setStatus("OPEN");
        FoodMenu foodMenu = this.foodMenuFactory.create(foodMenuDynamoEntity);
        Assert.assertNotNull(foodMenu);
        Assert.assertTrue(foodMenu.getStatus() == FoodMenu.Status.OPEN);
        System.out.println(foodMenu);
    }

    @Test
    public void createEntity() {
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setCreatedDatetime(LocalDateTime.now());
        foodMenu.setFoodPlaceId("1");
        foodMenu.setId("aabads-dar3rrfsf-afasf2");
        foodMenu.setName("Menú Regular");
        foodMenu.setStatus(FoodMenu.Status.OPEN);
        FoodMenuDynamoEntity foodMenuDynamoEntity = this.foodMenuFactory.create(foodMenu);
        Assert.assertNotNull(foodMenuDynamoEntity);
        Assert.assertTrue(StringUtils.equals(FoodMenu.Status.OPEN.name(), foodMenuDynamoEntity.getStatus()));
        System.out.println(foodMenuDynamoEntity);

    }
}
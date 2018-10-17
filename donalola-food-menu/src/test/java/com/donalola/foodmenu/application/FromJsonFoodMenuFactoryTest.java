package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

public class FromJsonFoodMenuFactoryTest {

    private FoodMenuFactory<FoodMenuJson> factory;

    @Before
    public void setUp() throws Exception {
        factory = new FromJsonFoodMenuFactory();
    }

    @Test
    public void createDomainWithoutItems() {
        FoodMenuJson json = new FoodMenuJson();
        json.setName("Regular");
        json.setFoodPlaceId("1");
        FoodMenu foodMenu = this.factory.create(json);
        System.out.println(foodMenu);
        Assert.assertNotNull(foodMenu);
        Assert.assertTrue(Optional.ofNullable(foodMenu.getCreatedDatetime()).isPresent());
        Assert.assertNull(foodMenu.getItems());
    }

    @Test
    public void createDomainWithItems() {
        FoodMenuJson json = new FoodMenuJson();
        json.setName("Regular");
        json.setFoodPlaceId("1");

        ItemMenuJson itemMenuJson = new ItemMenuJson();
        itemMenuJson.setDescription("Lomo fino saltado con papas fritas y arroz blanco");
        itemMenuJson.setName("Lomo Saltado");
        itemMenuJson.setPrice(new BigDecimal("11.50"));
        itemMenuJson.setQuantityAvailable(10);

        json.setItems(Arrays.asList(itemMenuJson));

        FoodMenu foodMenu = this.factory.create(json);
        System.out.println(foodMenu);
        Assert.assertNotNull(foodMenu);
        Assert.assertTrue(Optional.ofNullable(foodMenu.getCreatedDatetime()).isPresent());
        Assert.assertNotNull(foodMenu.getItems());
        Assert.assertTrue(CollectionUtils.size(foodMenu.getItems()) == 1);
    }
}
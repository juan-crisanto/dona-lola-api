package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.FoodMenuTestUtil;
import com.donalola.foodmenu.ItemMenu;
import com.donalola.foodmenu.domain.factory.FoodMenuFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    private FoodMenu createFoodMenu() {
        FoodMenu foodMenu = new FoodMenu();
        foodMenu.setCreatedDatetime(LocalDateTime.now());
        foodMenu.setFoodPlaceId("1");
        foodMenu.setId("aabads-dar3rrfsf-afasf2");
        foodMenu.setName("Menú Regular");
        foodMenu.setStatus(FoodMenu.Status.OPEN);
        return foodMenu;
    }

    @Test
    public void createJsonWithoutItems() {
        FoodMenu foodMenu = createFoodMenu();
        FoodMenuJson json = this.factory.create(foodMenu);
        Assert.assertNotNull(json);
        Assert.assertTrue(StringUtils.equals(foodMenu.getId(), json.getId()));
        Assert.assertTrue(CollectionUtils.isEmpty(json.getItems()));
        System.out.println(json);
    }

    @Test
    public void createJsonWithItems() {
        FoodMenu foodMenu = createFoodMenu();
        List<ItemMenu> itemMenuList = Arrays.asList(FoodMenuTestUtil.createItem());
        foodMenu.setItems(itemMenuList);
        FoodMenuJson json = this.factory.create(foodMenu);
        Assert.assertNotNull(json);
        Assert.assertTrue(StringUtils.equals(foodMenu.getId(), json.getId()));
        Assert.assertTrue(CollectionUtils.size(json.getItems()) == 1);
        System.out.println(json);
    }
}
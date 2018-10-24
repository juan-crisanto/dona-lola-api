package com.donalola.foodmenu;

import java.math.BigDecimal;

public class FoodMenuTestUtil {

    public static FoodMenu.Item createItem() {
        FoodMenu.Item itemMenu = new FoodMenu.Item();
        itemMenu.setName("Lomo Saltado");
        itemMenu.setDescription("Lomo fino saltado, acompañado con papas fritas y arroz blanco");
        itemMenu.setId("idmenu-13231-fasdf234-fasdf");
        itemMenu.setPrice(new BigDecimal("14.50"));
        itemMenu.setQuantityAvailable(6);
        return itemMenu;
    }
}

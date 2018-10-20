package com.donalola.foodmenu;

public interface ItemsMenu extends Iterable<ItemMenu> {

    ItemsMenu getByMenu(String menuId);

}

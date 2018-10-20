package com.donalola.foodmenu.domain.factory;

import com.donalola.foodmenu.ItemMenu;

public interface ItemMenuFactory<T> {

    ItemMenu create(T source);

    T create(ItemMenu itemMenu);

}

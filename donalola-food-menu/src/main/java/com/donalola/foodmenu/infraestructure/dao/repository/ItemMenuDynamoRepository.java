package com.donalola.foodmenu.infraestructure.dao.repository;

import com.donalola.core.SimpleIterable;
import com.donalola.foodmenu.ItemMenu;
import com.donalola.foodmenu.ItemsMenu;
import com.donalola.foodmenu.domain.dao.repository.ItemMenuRepository;
import com.donalola.foodmenu.domain.factory.ItemMenuFactory;
import com.donalola.foodmenu.infraestructure.dao.entity.ItemMenuDynamoEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ItemMenuDynamoRepository implements ItemMenuRepository, ItemsMenu {

    private final ItemMenuDynamoCrudRepository itemMenuDynamoCrudRepository;
    private final ItemMenuFactory<ItemMenuDynamoEntity> itemMenuFactory;

    public ItemMenuDynamoRepository(ItemMenuDynamoCrudRepository itemMenuDynamoCrudRepository, ItemMenuFactory<ItemMenuDynamoEntity> itemMenuFactory) {
        this.itemMenuDynamoCrudRepository = itemMenuDynamoCrudRepository;
        this.itemMenuFactory = itemMenuFactory;
    }

    @Override
    public Iterator<ItemMenu> iterator() {
        Iterable<ItemMenuDynamoEntity> entityList = this.itemMenuDynamoCrudRepository.findAll();
        List<ItemMenu> itemMenuList = new ArrayList<>(CollectionUtils.size(entityList));
        entityList.forEach(itemMenuDynamoEntity -> itemMenuList.add(this.itemMenuFactory.create(itemMenuDynamoEntity)));
        return itemMenuList.iterator();
    }

    @Override
    public ItemMenu add(ItemMenu itemMenu) {
        if (!Optional.ofNullable(itemMenu.getMenuId()).isPresent() || StringUtils.isEmpty(itemMenu.getMenuId())) {
            throw new IllegalArgumentException("Para agregar un ítem es necesario especificar un menú");
        }
        ItemMenuDynamoEntity entity = this.itemMenuFactory.create(itemMenu);
        ItemMenuDynamoEntity savedEntity = this.itemMenuDynamoCrudRepository.save(entity);
        return this.itemMenuFactory.create(savedEntity);
    }

    @Override
    public ItemsMenu getByMenu(String menuId) {
        List<ItemMenuDynamoEntity> entityList = this.itemMenuDynamoCrudRepository.findAllByMenuId(menuId);
        return createSimpleIterable(entityList);
    }


    private SimpleItemsMenuIterable createSimpleIterable(Iterable<ItemMenuDynamoEntity> iterable) {
        List<ItemMenu> itemMenuList = new ArrayList<>(IterableUtils.size(iterable));
        iterable.forEach(itemMenuDynamoEntity -> itemMenuList.add(this.itemMenuFactory.create(itemMenuDynamoEntity)));
        return new SimpleItemsMenuIterable(itemMenuList.iterator());
    }

    public static class SimpleItemsMenuIterable extends SimpleIterable<ItemMenu> implements ItemsMenu {

        public SimpleItemsMenuIterable(Iterator<ItemMenu> iterator) {
            super(iterator);
        }

        @Override
        public ItemsMenu getByMenu(String menuId) {
            throw new UnsupportedOperationException("UnsupportedOperationException");
        }
    }
}

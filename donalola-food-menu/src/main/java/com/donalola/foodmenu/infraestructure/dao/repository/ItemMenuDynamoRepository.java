package com.donalola.foodmenu.infraestructure.dao.repository;

import com.donalola.foodmenu.ItemMenu;
import com.donalola.foodmenu.domain.dao.repository.ItemMenuRepository;
import com.donalola.foodmenu.domain.factory.ItemMenuFactory;
import com.donalola.foodmenu.infraestructure.dao.entity.ItemMenuDynamoEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
public class ItemMenuDynamoRepository implements ItemMenuRepository {

    private final ItemMenuDynamoCrudRepository itemMenuDynamoCrudRepository;
    private final ItemMenuFactory<ItemMenuDynamoEntity> itemMenuFactory;

    public ItemMenuDynamoRepository(ItemMenuDynamoCrudRepository itemMenuDynamoCrudRepository, ItemMenuFactory<ItemMenuDynamoEntity> itemMenuFactory) {
        this.itemMenuDynamoCrudRepository = itemMenuDynamoCrudRepository;
        this.itemMenuFactory = itemMenuFactory;
    }

    @Override
    public ItemMenu add(ItemMenu itemMenu) {
        if (!Optional.ofNullable(itemMenu.getMenuId()).isPresent() || StringUtils.isEmpty(itemMenu.getMenuId())) {
            throw new IllegalArgumentException("Para agregar un ítem es necesario especificar un menú");
        }
        ItemMenuDynamoEntity entity = this.itemMenuFactory.create(itemMenu);
        entity.setCreatedTime(LocalDateTime.now());
        ItemMenuDynamoEntity savedEntity = this.itemMenuDynamoCrudRepository.save(entity);
        return this.itemMenuFactory.create(savedEntity);
    }
}

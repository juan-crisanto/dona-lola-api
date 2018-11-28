package com.donalola.foodmenu.application;

import com.donalola.foodmenu.FoodMenu;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface FoodMenuJsonToDomainMapper {

    FoodMenuJsonToDomainMapper MAPPER = Mappers.getMapper(FoodMenuJsonToDomainMapper.class);

    FoodMenu jsonToDomain(FoodMenuJson json);

    FoodMenuJson domainToJson(FoodMenu foodMenu);

    @AfterMapping
    default void setDefaultImage(FoodMenu foodMenu, @MappingTarget FoodMenuJson foodMenuJson) {
        if (CollectionUtils.isEmpty(foodMenuJson.getItems())) {
            return;
        }
        foodMenuJson.getItems().parallelStream().forEach(itemJson -> {
            if (StringUtils.isEmpty(itemJson.getImage())) {
                itemJson.setImage(FoodMenuConstant.DEFAULT_ITEM_IMAGE);
            }
        });
    }
}

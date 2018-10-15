package com.donalola.foodmenu.infraestructure.dao.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.donalola.commons.dynamodb.util.DynamoDBConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@DynamoDBTable(tableName = "FOOD_MENU_ITEMS")
public class FoodMenuItemDynamoEntity {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute(attributeName = "idFoodPlace")
    private String idFoodPlace;

    @DynamoDBTypeConverted(converter = DynamoDBConverter.LocalDateTimeConverter.class)
    @DynamoDBAttribute(attributeName = "createdDateTime")
    private LocalDateTime createdTime;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "description")
    private String description;

    @DynamoDBAttribute(attributeName = "quantityAvailable")
    private Integer quantityAvailable;

    @DynamoDBTypeConverted(converter = DynamoDBConverter.BigDecimalConverter.class)
    @DynamoDBAttribute(attributeName = "price")
    private BigDecimal price;


}

package com.donalola.application.validation.order.service;

import com.donalola.CustomerDetails;
import com.donalola.ItemMenuDetails;
import com.donalola.application.util.SecurityApplicationUtil;
import com.donalola.application.validation.service.ValidateService;
import com.donalola.authentication.AppUser;
import com.donalola.foodmenu.FoodMenu;
import com.donalola.foodmenu.domain.FoodMenuManager;
import com.donalola.orders.application.OrderJson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ValidateOrderJsonService implements ValidateService {

    private final FoodMenuManager foodMenuManager;

    public ValidateOrderJsonService(FoodMenuManager foodMenuManager) {
        this.foodMenuManager = foodMenuManager;
    }

    @Override
    public void validateJsonRequest(Object jsonRequest) {
        OrderJson json = (OrderJson) jsonRequest;
        if (log.isDebugEnabled()) {
            log.debug("Validating jsonRequest for Order: " + json);
        }
        completeCustomerInfo(json);
        completeAndValidateItems(json);
        return;
    }

    private void completeCustomerInfo(OrderJson orderJson) {
        AppUser appUser = SecurityApplicationUtil.getUser();
        orderJson.setCustomerId(appUser.getUsername());
        orderJson.setCustomerDetails(new CustomerDetails(appUser.getName(), appUser.getEmail(), StringUtils.EMPTY));
    }

    private void completeAndValidateItems(OrderJson orderJson) {
        Set<String> menuIds = retrieveInvolvedMenuIds(orderJson.getItems());
        for (String menuId : menuIds) {
            FoodMenu foodMenu = this.foodMenuManager.get(menuId);
            Map<String, Integer> itemsAndQuantity = getQuantityItems(orderJson.getItems(), menuId);
            hasEnougStock(foodMenu, itemsAndQuantity);
            completeItemsInfo(foodMenu, orderJson);
        }
    }

    private void completeItemsInfo(final FoodMenu foodMenu, OrderJson orderJson) {
        List<OrderJson.ItemJson> completeItemJsonList = new ArrayList<>(CollectionUtils.size(orderJson.getItems()));
        for (OrderJson.ItemJson itemJson : orderJson.getItems()) {
            FoodMenu.Item itemMenu = foodMenu.getItem(itemJson.getItemMenuId());
            itemJson.setItemMenuDetails(new ItemMenuDetails(itemMenu.getName(), itemMenu.getDescription(), itemMenu.getPrice()));
            completeItemJsonList.add(itemJson);
        }
        orderJson.setItems(completeItemJsonList);
    }

    private Set<String> retrieveInvolvedMenuIds(final List<OrderJson.ItemJson> itemJsonList) {
        Set<String> menuIdSet = itemJsonList.parallelStream()
                .map(itemJson -> itemJson.getFoodMenuId())
                .collect(Collectors.toSet());
        return menuIdSet;
    }

    private void hasEnougStock(final FoodMenu foodMenu, final Map<String, Integer> itemsAndQuantity) {
        itemsAndQuantity.forEach((s, integer) -> {
            foodMenu.isItemAvailable(s, integer);
        });
    }

    private Map<String, Integer> getQuantityItems(final List<OrderJson.ItemJson> itemJsonList, final String menuId) {
        return itemJsonList.parallelStream()
                .filter(itemJson -> StringUtils.equals(menuId, itemJson.getFoodMenuId()))
                .collect(Collectors.toMap(OrderJson.ItemJson::getItemMenuId, OrderJson.ItemJson::getQuantity));
    }

    @Override
    public boolean supports(Class<?> supportedClass) {
        return OrderJson.class.isAssignableFrom(supportedClass);
    }
}

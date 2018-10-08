package com.donalola.core.manager;

import com.donalola.core.ErrorConstant;
import com.donalola.core.service.BaseService;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAbstractManager<T extends BaseService> {

    protected abstract List<T> getServiceList();

    /**
     * Return a list with all compatible implementations with the given type
     *
     * @param toTest
     * @return
     */
    protected List<T> getSupportedServices(Class<?> toTest) {
        List<T> servicesList = new ArrayList<>();
        for (T service : getServiceList()) {
            if (!service.supports(toTest)) {
                continue;
            }
            servicesList.add(service);
        }
        return servicesList;
    }

    /**
     * Return first compatible implementation with the given type
     *
     * @param toTest
     * @return
     */
    protected T getService(Class<?> toTest) {
        for (T service : getServiceList()) {
            if (!service.supports(toTest)) {
                continue;
            }
            return service;
        }
        throw new IllegalArgumentException(ErrorConstant.NO_IMPLEMENTATION_FOR_REQUEST);
    }
}

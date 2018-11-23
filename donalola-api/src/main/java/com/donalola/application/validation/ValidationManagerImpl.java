package com.donalola.application.validation;

import com.donalola.application.validation.service.ValidateService;
import com.donalola.core.manager.BaseAbstractManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidationManagerImpl extends BaseAbstractManager<ValidateService> implements ValidationManager {

    private final List<ValidateService> serviceList;

    public ValidationManagerImpl(List<ValidateService> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public void validateJsonRequest(Object jsonRequest) {
        ValidateService validateService = getService(jsonRequest.getClass());
        validateService.validateJsonRequest(jsonRequest);
    }

    @Override
    protected List<ValidateService> getServiceList() {
        return this.serviceList;
    }
}

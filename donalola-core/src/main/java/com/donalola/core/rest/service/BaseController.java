package com.donalola.core.rest.service;

import com.donalola.application.PreventCorruption;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.BindingResult;

@PreventCorruption
public abstract class BaseController {

    public void evaluateValidation(BindingResult validation) {
        if (validation.hasErrors() && CollectionUtils.isNotEmpty(validation.getAllErrors())) {
            String message = validation.getAllErrors().get(0).getDefaultMessage();
            throw new RuntimeException(message);
        }
    }
}

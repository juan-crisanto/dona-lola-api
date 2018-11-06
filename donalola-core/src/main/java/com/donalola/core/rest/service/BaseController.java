package com.donalola.core.rest.service;

import com.donalola.application.PreventCorruption;
import com.donalola.util.logging.NeedLog;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.BindingResult;

@PreventCorruption
@NeedLog
public abstract class BaseController {

    public void evaluateValidation(BindingResult validation) {
        if (validation.hasErrors() && CollectionUtils.isNotEmpty(validation.getAllErrors())) {
            String message = validation.getAllErrors().get(0).getDefaultMessage();
            throw new RuntimeException(message);
        }
    }
}

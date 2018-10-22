package com.donalola.application.validation.service;

import com.donalola.core.service.BaseService;

public interface ValidateService extends BaseService {

    void validateJsonRequest(Object jsonRequest);

}

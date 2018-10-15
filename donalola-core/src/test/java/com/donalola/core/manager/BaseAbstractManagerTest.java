package com.donalola.core.manager;

import com.donalola.core.service.BaseService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

public class BaseAbstractManagerTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private BaseService mockupOneService;
    @Mock
    private BaseService mockupTwoService;
    private BaseAbstractManager<BaseService> baseServiceBaseAbstractManager;

    @Before
    public void setUp() throws Exception {
        Mockito.when(mockupOneService.supports(MockupRequestDto.class)).thenReturn(true);
        Mockito.when(mockupOneService.supports(MockUpOneRequestDto.class)).thenReturn(true);
        Mockito.when(mockupOneService.supports(MockUpTwoRequestDto.class)).thenReturn(false);
        Mockito.when(mockupTwoService.supports(MockupRequestDto.class)).thenReturn(true);
        Mockito.when(mockupTwoService.supports(MockUpOneRequestDto.class)).thenReturn(false);
        Mockito.when(mockupTwoService.supports(MockUpTwoRequestDto.class)).thenReturn(true);

        baseServiceBaseAbstractManager = new BaseAbstractManager<BaseService>() {
            @Override
            protected List<BaseService> getServiceList() {
                List<BaseService> baseServiceList = new ArrayList<>(2);
                baseServiceList.add(mockupOneService);
                baseServiceList.add(mockupTwoService);
                return baseServiceList;
            }
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void NonSupportedRequest() {
        this.baseServiceBaseAbstractManager.getService(NonSupportedRequestDto.class);
    }

    @Test
    public void AnySupportedRequest() {
        List<BaseService> serviceList = this.baseServiceBaseAbstractManager.getSupportedServices(NonSupportedRequestDto.class);
        Assert.assertTrue(CollectionUtils.isEmpty(serviceList));
    }

    @Test
    public void getServiceList() {
        Assert.assertTrue(2 == this.baseServiceBaseAbstractManager.getServiceList().size());
    }

    @Test
    public void getSupportedServices() {
        List<BaseService> baseServiceList = this.baseServiceBaseAbstractManager.getSupportedServices(MockupRequestDto.class);
        Assert.assertTrue(2 == baseServiceList.size());
    }

    @Test
    public void getService() {
        BaseService baseService = this.baseServiceBaseAbstractManager.getService(MockUpOneRequestDto.class);
        Assert.assertTrue(StringUtils.equals("mockupOneService", baseService.toString()));
        BaseService baseServiceTwo = this.baseServiceBaseAbstractManager.getService(MockUpTwoRequestDto.class);
        Assert.assertTrue(StringUtils.equals("mockupTwoService", baseServiceTwo.toString()));
    }
}

interface MockupRequestDto {
}

class NonSupportedRequestDto implements MockupRequestDto {
}

class MockUpOneRequestDto implements MockupRequestDto {
}

class MockUpTwoRequestDto implements MockupRequestDto {
}
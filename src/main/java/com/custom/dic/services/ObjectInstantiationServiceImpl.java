package com.custom.dic.services;

import com.custom.dic.exceptions.BeanException;
import com.custom.dic.exceptions.InstantiationException;
import com.custom.dic.exceptions.PreDestroyException;
import com.custom.dic.models.ServiceBeanDetails;
import com.custom.dic.models.ServiceDetails;

import java.lang.reflect.Constructor;

public class ObjectInstantiationServiceImpl implements ObjectInstantiationService {
    private static final String INVALID_PARAMETERS_COUNT_MSG     = "Invalid parameters count for '%";

    @Override
    public void createInstance(ServiceDetails<?> serviceDetails, Object... constructorParams) throws InstantiationException {
        Constructor<?> targetConstructor = serviceDetails.getTargetConstructor();

        if (constructorParams.length != targetConstructor.getParameterCount()) {
            throw new InstantiationException(String.format(INVALID_PARAMETERS_COUNT_MSG, serviceDetails.getServiceType().getName()));
        }
    }

    @Override
    public void createBeanInstance(ServiceBeanDetails<?> serviceBeanDetails) throws BeanException {

    }

    @Override
    public void destroyInstance(ServiceDetails<?> serviceDetails) throws PreDestroyException {

    }
}

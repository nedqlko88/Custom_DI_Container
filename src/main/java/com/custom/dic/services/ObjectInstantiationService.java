package com.custom.dic.services;

import com.custom.dic.exceptions.BeanException;
import com.custom.dic.exceptions.ServiceInstantiationException;
import com.custom.dic.exceptions.PreDestroyException;
import com.custom.dic.models.ServiceBeanDetails;
import com.custom.dic.models.ServiceDetails;

public interface ObjectInstantiationService {

    void createInstance(ServiceDetails<?> serviceDetails, Object... constructorParams) throws ServiceInstantiationException;

    void createBeanInstance(ServiceBeanDetails<?> serviceBeanDetails) throws BeanException;

    void destroyInstance(ServiceDetails<?> serviceDetails) throws PreDestroyException;

}

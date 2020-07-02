package com.custom.dic.services;

import com.custom.dic.exceptions.ServiceInstantiationException;
import com.custom.dic.models.ServiceDetails;

import java.util.List;
import java.util.Set;

public interface ServicesInstantiationService {
    List<ServiceDetails<?>> instantiateServicesAndBeans(Set<ServiceDetails<?>> mappedServices) throws ServiceInstantiationException;
}

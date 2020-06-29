package com.custom.dic.services;

import com.custom.dic.models.ServiceDetails;

import java.util.Set;

public interface ServicesScanningService {

    Set<ServiceDetails<?>> mapServices(Set<Class<?>> locatedClasses);
}

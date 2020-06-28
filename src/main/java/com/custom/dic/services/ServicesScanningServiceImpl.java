package com.custom.dic.services;

import com.custom.dic.annotations.Service;
import com.custom.dic.config.configurations.CustomAnnotationsConfiguration;
import com.custom.dic.models.ServiceDetails;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

public class ServicesScanningServiceImpl implements ServicesScanningService{
    private final CustomAnnotationsConfiguration configuration;

    public ServicesScanningServiceImpl(CustomAnnotationsConfiguration customAnnotationsConfiguration) {
        this.configuration = customAnnotationsConfiguration;
    }

    @Override
    public Set<ServiceDetails<?>> mapServices(Set<Class<?>> locatedClasses) {
       final Set<ServiceDetails<?>> serviceDetailsStorage = new HashSet<>();
       final Set<Class<? extends Annotation>> customServiceAnnotations = configuration.getCustomServiceAnnotations();
       customServiceAnnotations.add(Service.class);

        for (Class<?> cls : locatedClasses) {
                if ((cls.isInterface())) {
                    continue;
                }

            for (Annotation annotation : cls.getAnnotations()) {
                if (customServiceAnnotations.contains(annotation)) {
                    ServiceDetails<?> serviceDetails = new ServiceDetails<>(
                        cls,
                            annotation,

                    )
                }
            }



        }

       return serviceDetails;
    }

    private Constructor<?> findSuitableConstructor(Class<?> cls) {
        Constructor<?> constructor = cls.getDeclaredConstructors()[0];
    }
}

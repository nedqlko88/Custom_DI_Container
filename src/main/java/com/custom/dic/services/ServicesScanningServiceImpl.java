package com.custom.dic.services;

import com.custom.dic.annotations.*;
import com.custom.dic.config.configurations.CustomAnnotationsConfiguration;
import com.custom.dic.models.ServiceDetails;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ServicesScanningServiceImpl implements ServicesScanningService {
    private final CustomAnnotationsConfiguration configuration;

    public ServicesScanningServiceImpl(CustomAnnotationsConfiguration customAnnotationsConfiguration) {
        this.configuration = customAnnotationsConfiguration;
        this.init();
    }

    @Override
    public Set<ServiceDetails<?>> mapServices(Set<Class<?>> locatedClasses) {
        final Set<ServiceDetails<?>> serviceDetailsStorage = new HashSet<>();
        final Set<Class<? extends Annotation>> customServiceAnnotations = configuration.getCustomServiceAnnotations();

        for (Class<?> cls : locatedClasses) {
            if ((cls.isInterface())) {
                continue;
            }

            for (Annotation annotation : cls.getAnnotations()) {
                if (customServiceAnnotations.contains(annotation.annotationType())) {
                    ServiceDetails<?> serviceDetails = new ServiceDetails(
                            cls,
                            annotation,
                            this.findSuitableConstructor(cls),
                            this.getVoidMethodWithZeroParamsAndAnnotation(PostConstruct.class, cls),
                            this.getVoidMethodWithZeroParamsAndAnnotation(PreDestroy.class, cls),
                            this.findBeans(cls)
                    );

                    serviceDetailsStorage.add(serviceDetails);
                }
            }
        }

        return serviceDetailsStorage;
    }

    private Constructor<?> findSuitableConstructor(Class<?> cls) {
        for (Constructor<?> ctr : cls.getDeclaredConstructors()) {
            if (ctr.isAnnotationPresent(Autowired.class)) {
                ctr.setAccessible(true);
                return ctr;
            }
        }
        return cls.getDeclaredConstructors()[0];
    }

    private Method getVoidMethodWithZeroParamsAndAnnotation(Class<? extends Annotation> annotation, Class<?> cls) {
        for (Method method : cls.getDeclaredMethods()) {
            if (method.getParameterCount() != 0 ||
                    (method.getReturnType() != void.class && method.getReturnType() != Void.class) ||
                    !method.isAnnotationPresent(annotation)) {
                continue;
            }

            method.setAccessible(true);
            return method;
        }

        return null;
    }

    private Method[] findBeans(Class<?> cls) {
        Set<Class<? extends Annotation>> customBeanAnnotations = this.configuration.getCustomBeanAnnotations();
        Set<Method> beanMethods = new HashSet<>();

        for (Method method : cls.getDeclaredMethods()) {
            if (method.getParameterCount() != 0 || method.getReturnType() == void.class ||
                    method.getReturnType() == Void.class) {
                continue;
            }

            for (Class<? extends Annotation> beanAnnotation : customBeanAnnotations) {
                if (method.isAnnotationPresent(beanAnnotation)) {
                    beanMethods.add(method);
                }
            }
        }

        return beanMethods.toArray(Method[]::new);
    }

    private void init() {
        this.configuration.getCustomServiceAnnotations().add(Service.class);
        this.configuration.getCustomBeanAnnotations().add(Bean.class);
    }
}

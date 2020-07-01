package com.custom.dic.services;

import com.custom.dic.config.configurations.InstantiationConfiguration;
import com.custom.dic.exceptions.ServiceInstantiationException;
import com.custom.dic.models.EnqueuedServiceDetails;
import com.custom.dic.models.ServiceBeanDetails;
import com.custom.dic.models.ServiceDetails;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ServicesInstantiationServiceImpl implements ServicesInstantiationService {

    private static final String MAX_NUMBER_OF_ALLOWED_ITERATIONS_REACHED_MSG = "Maximum number of allowed iterations was reached '%s'.";

    public static final String COULD_NOT_FIND_CONSTRUCTOR_PARAM_MSG = "Could not create instance of '%s'. Parameter '%s' was not found";

    private final InstantiationConfiguration configuration;

    private final ObjectInstantiationService instantiationService;

    private final LinkedList<EnqueuedServiceDetails> enqueuedServiceDetails;

    private final List<Class<?>> allAvailableClasses;

    private final List<ServiceDetails<?>> instantiatedServices;

    public ServicesInstantiationServiceImpl(InstantiationConfiguration configuration, ObjectInstantiationService instantiationService) {
        this.configuration = configuration;
        this.instantiationService = instantiationService;
        this.enqueuedServiceDetails = new LinkedList<>();
        this.allAvailableClasses = new ArrayList<>();
        this.instantiatedServices = new ArrayList<>();
    }

    @Override
    public List<ServiceDetails<?>> instantiateServicesAndBeans(Set<ServiceDetails<?>> mappedServices) throws ServiceInstantiationException {
        this.init(mappedServices);
        this.checkForMissingServices(mappedServices);

        int counter = 0;
        int maximumAllowedIterations = this.configuration.getMaximumAllowedIterations();
        while (!this.enqueuedServiceDetails.isEmpty()) {
            if (counter > maximumAllowedIterations) {
                throw new ServiceInstantiationException(String.format(MAX_NUMBER_OF_ALLOWED_ITERATIONS_REACHED_MSG, maximumAllowedIterations));
            }

            EnqueuedServiceDetails enqueuedServiceDetails = this.enqueuedServiceDetails.removeFirst();

            if (enqueuedServiceDetails.isResolved()) {
                ServiceDetails<?> serviceDetails = enqueuedServiceDetails.getServiceDetails();
                Object[] dependencyInstances = enqueuedServiceDetails.getDependencyInstances();

                this.instantiationService.createInstance(serviceDetails, dependencyInstances);
                this.registerInstantiatedService(serviceDetails);
                this.registerBeans(serviceDetails);
            } else {
                this.enqueuedServiceDetails.addLast(enqueuedServiceDetails);
                counter++;
            }
        }

        return this.instantiatedServices;
    }

    private void registerBeans(ServiceDetails<?> serviceDetails) {
        for (Method beanMethod : serviceDetails.getBeans()) {
            ServiceBeanDetails<?> beanDetails = new ServiceBeanDetails<>(beanMethod.getReturnType(), beanMethod, serviceDetails);
            this.instantiationService.createBeanInstance(beanDetails);
            this.registerInstantiatedService(beanDetails);

        }
    }

    private void registerInstantiatedService(ServiceDetails<?> serviceDetails) {
        if (!(serviceDetails instanceof ServiceBeanDetails)) {
            this.updateDependantServices(serviceDetails);
        }

        this.instantiatedServices.add(serviceDetails);


        for (EnqueuedServiceDetails enqueuedService : this.enqueuedServiceDetails) {
            if (enqueuedService.isDependencyRequired(serviceDetails.getServiceType())) {
                enqueuedService.addDependencyInstance(serviceDetails.getInstance());
            }
        }

    }

    private void updateDependantServices(ServiceDetails<?> newService) {
        for (Class<?> parameterType : newService.getTargetConstructor().getParameterTypes()) {
            for (ServiceDetails<?> serviceDetails : this.instantiatedServices) {
                if (parameterType.isAssignableFrom(serviceDetails.getServiceType())) {
                    serviceDetails.addDependantService(newService);
                }
            }
        }
    }

    private void checkForMissingServices(Set<ServiceDetails<?>> mappedServices) throws ServiceInstantiationException {
        for (ServiceDetails<?> serviceDetails : mappedServices) {
            for (Class<?> parameterType : serviceDetails.getTargetConstructor().getParameterTypes()) {
                if (!this.isAssignableTypePresent(parameterType)) {
                    throw new ServiceInstantiationException(
                            String.format(COULD_NOT_FIND_CONSTRUCTOR_PARAM_MSG,
                                    serviceDetails.getServiceType().getName(),
                                    parameterType.getName()
                            )
                    );
                }
            }
        }
    }

    private boolean isAssignableTypePresent(Class<?> cls) {
        for (Class<?> serviceType : this.allAvailableClasses) {
            if (cls.isAssignableFrom(serviceType)) {
                return true;
            }
        }

        return false;
    }

    private void init(Set<ServiceDetails<?>> mappedServices) {
        this.enqueuedServiceDetails.clear();
        this.allAvailableClasses.clear();
        this.instantiatedServices.clear();

        for (ServiceDetails<?> serviceDetails : mappedServices) {
            this.enqueuedServiceDetails.add(new EnqueuedServiceDetails(serviceDetails));
            this.allAvailableClasses.add(serviceDetails.getServiceType());
            this.allAvailableClasses.addAll(Arrays.stream(serviceDetails.getBeans())
                    .map(Method::getReturnType)
                    .collect(Collectors.toList()));
        }
    }
}

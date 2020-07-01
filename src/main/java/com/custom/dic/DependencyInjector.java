package com.custom.dic;

import com.custom.dic.config.Configuration;
import com.custom.dic.enums.DirectoryType;
import com.custom.dic.models.Directory;
import com.custom.dic.models.ServiceDetails;
import com.custom.dic.services.*;

import java.util.List;
import java.util.Set;

public class DependencyInjector {
    public static void main(String[] args) {
        run(DependencyInjector.class);
    }

    public static void run(Class<?> startupClass) {
        run(startupClass, new Configuration());
    }

    public static void run(Class<?> startupClass, Configuration configuration) {
        ServicesScanningService scanningService = new ServicesScanningServiceImpl(configuration.annotations());
        ObjectInstantiationService objectInstantiationService = new ObjectInstantiationServiceImpl();
        ServicesInstantiationService instantiationService = new ServicesInstantiationServiceImpl(
                configuration.instantiations(),
                objectInstantiationService
        );

        DirectoryResolver directoryResolver = new DirectoryResolverImpl();
        Directory directory = directoryResolver.resolveDirectory(startupClass);
//        Directory directory = new DirectoryResolverImpl().resolveDirectory(startupClass);

        ClassLocator classLocator = new ClassLocatorForDirectory();
        if (directory.getDirectoryType() == DirectoryType.JAR_FILE) {
            classLocator = new ClassLocatorForJarFile();
        }

        Set<Class<?>> locatedClasses = classLocator.locateClasses(directory.getDirectory());

        Set<ServiceDetails<?>> mappedServices = scanningService.mapServices(locatedClasses);
        List<ServiceDetails<?>> serviceDetails = instantiationService.instantiateServicesAndBeans(mappedServices);


    }
}

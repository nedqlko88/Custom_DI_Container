package com.custom.dic;

import com.custom.dic.annotations.Autowired;
import com.custom.dic.annotations.PostConstruct;
import com.custom.dic.annotations.Service;
import com.custom.dic.config.Configuration;
import com.custom.dic.enums.DirectoryType;
import com.custom.dic.models.Directory;
import com.custom.dic.models.ServiceDetails;
import com.custom.dic.services.*;
import com.custom.dic.test.*;

import java.util.List;
import java.util.Set;

@Service
public class DependencyInjector {

    private final OtherServiceConstract otherService;

    private final ModelMapper modelMapper;

    public DependencyInjector(OtherService otherService, ModelMapper modelMapper) {
        this.otherService = otherService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        System.out.println("Hello," + this.otherService.getClass().getName());
        System.out.println(this.modelMapper.map());
    }

    public static void main(String[] args) {
        run(DependencyInjector.class,
                new Configuration().annotations().
                        addCustomServiceAnnotation(CustomService.class).
                        addCustomBeanAnnotation(CustomBean.class).and());
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

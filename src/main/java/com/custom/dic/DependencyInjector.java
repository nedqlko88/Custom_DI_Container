package com.custom.dic;

import com.custom.dic.config.Configuration;
import com.custom.dic.enums.DirectoryType;
import com.custom.dic.models.Directory;
import com.custom.dic.services.*;

import java.util.Set;

public class DependencyInjector {
    public static void main(String[] args) {
    run(DependencyInjector.class);
    }

    public static void run(Class<?> startupClass) {
        run(startupClass, new Configuration());
    }

    public static void run(Class<?> startupClass, Configuration configuration) {
        System.out.println("Dir is ");
        Directory directory = new DirectoryResolverImpl().resolveDirectory(startupClass);

        ClassLocator classLocator = new ClassLocatorForDirectory();
        if (directory.getDirectoryType() == DirectoryType.JAR_FILE) {
            classLocator = new ClassLocatorForJarFile();
        }

        Set<Class<?>> classes = classLocator.locateClasses(directory.getDirectory());
        System.out.println(classes);
    }
}

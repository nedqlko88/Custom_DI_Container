package com.custom.dic;

import com.custom.dic.config.Configuration;

public class DependencyInjector {
    public static void main(String[] args) {
    run(DependencyInjector.class);
    }

    public static void run(Class<?> startupClass) {
        run(startupClass, new Configuration());
    }

    public static void run(Class<?> startupClass, Configuration configuration) {
        System.out.println("Dir is ");
        System.out.println(startupClass.getProtectionDomain().getCodeSource().getLocation().getFile());
//        it will give us this dir /C:/Projects/Java/CustomDIContainer/target/classes/

    }
}

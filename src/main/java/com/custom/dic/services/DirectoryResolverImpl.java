package com.custom.dic.services;

import com.custom.dic.enums.DirectoryType;
import com.custom.dic.models.Directory;

import java.io.File;

public class DirectoryResolverImpl implements DirectoryResolver {

    private static final String JAR_FILE_EXTENSION = ".jar";

    @Override
    public Directory resolveDirectory(Class<?> startupClass) {
        final String directory = this.getDirectory(startupClass);
        return new Directory(directory, this.getDirectoryType(directory));
    }

    private String getDirectory(Class<?> cls) {
       return cls.getProtectionDomain().getCodeSource().getLocation().getFile();
    }

    private DirectoryType getDirectoryType(String directory) {
        File file = new File(directory);

        if (!file.isDirectory() && directory.endsWith(JAR_FILE_EXTENSION)) {
            return DirectoryType.JAR_FILE;
        }

        return DirectoryType.DIRECTORY;
    }



}

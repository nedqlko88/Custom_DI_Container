package com.custom.dic.models;

import com.custom.dic.enums.DirectoryType;

public class Directory {
    private final String directory;
    private final DirectoryType directoryType;

    public Directory(String directory, DirectoryType directoryType) {
        this.directory = directory;
        this.directoryType = directoryType;
    }

    public DirectoryType getDirectoryType() {
        return directoryType;
    }

    public String getDirectory() {
        return directory;
    }
}

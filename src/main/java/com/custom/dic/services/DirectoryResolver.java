package com.custom.dic.services;

import com.custom.dic.models.Directory;

public interface DirectoryResolver {

    Directory resolveDirectory(Class<?> startupClass);
}

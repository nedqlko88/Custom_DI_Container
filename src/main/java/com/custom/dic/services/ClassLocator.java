package com.custom.dic.services;

import com.custom.dic.exceptions.ClassLocationException;
import com.custom.dic.models.Directory;

import java.util.Set;

public interface ClassLocator {
    Set<Class<?>> locateClasses(String directory) throws ClassLocationException;
}

package com.custom.dic.config.configurations;

import com.custom.dic.config.BaseSubConfiguration;
import com.custom.dic.config.Configuration;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomAnnotationsConfiguration extends BaseSubConfiguration {

    private final Set<Class<? extends Annotation>> customServiceAnnotations;

    private final Set<Class<? extends Annotation>> customBeanAnnotations;

    public CustomAnnotationsConfiguration(Configuration parentConfig) {
        super(parentConfig);
        this.customServiceAnnotations = new HashSet<>();
        this.customBeanAnnotations = new HashSet<>();
    }

    public CustomAnnotationsConfiguration addCustomServiceAnnotation(Class<? extends Annotation> annotation) {
        this.customServiceAnnotations.add(annotation);
        return this;
    }

    public CustomAnnotationsConfiguration addCustomServiceAnnotations(Class<? extends Annotation>... annotations) {
        this.customServiceAnnotations.addAll(Arrays.asList(annotations));
        return this;
    }

    public CustomAnnotationsConfiguration addCustomBeanAnnotation(Class<? extends Annotation> annotation) {
        this.customBeanAnnotations.add(annotation);
        return this;
    }

    public CustomAnnotationsConfiguration addCustomBeanAnnotations(Class<? extends Annotation>... annotations) {
        this.customBeanAnnotations.addAll(Arrays.asList(annotations));
        return this;
    }

    public Set<Class<? extends Annotation>> getCustomBeanAnnotations() {
        return customBeanAnnotations;
    }

    public Set<Class<? extends Annotation>> getCustomServiceAnnotations() {
        return customServiceAnnotations;
    }
}

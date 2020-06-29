package com.custom.dic.config;

import com.custom.dic.config.configurations.CustomAnnotationsConfiguration;

public abstract class BaseSubConfiguration {
    private final Configuration parentConfig;

    protected BaseSubConfiguration(Configuration parentConfig) {
        this.parentConfig = parentConfig;
    }
     public Configuration and() {
        return  this.parentConfig;
     }
}

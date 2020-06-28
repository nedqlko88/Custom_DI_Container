package com.custom.dic.config;

import com.custom.dic.config.configurations.CustomAnnotationsConfiguration;

public class Configuration {

    private final CustomAnnotationsConfiguration annotations;

    public Configuration() {
        this.annotations = new CustomAnnotationsConfiguration(this);
    }

    public CustomAnnotationsConfiguration annotations() {
        return this.annotations();
    }

    public Configuration build() {
        return this;
    }
}

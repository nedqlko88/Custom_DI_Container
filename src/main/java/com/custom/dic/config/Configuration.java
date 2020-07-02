package com.custom.dic.config;

import com.custom.dic.config.configurations.CustomAnnotationsConfiguration;
import com.custom.dic.config.configurations.InstantiationConfiguration;

public class Configuration {

    private final CustomAnnotationsConfiguration annotations;

    private final InstantiationConfiguration instantiations;

    public Configuration() {
        this.annotations = new CustomAnnotationsConfiguration(this);
        this.instantiations = new InstantiationConfiguration(this);
    }

    public CustomAnnotationsConfiguration annotations() {
        return this.annotations;
    }

    public InstantiationConfiguration instantiations() {
        return this.instantiations;
    }

    public Configuration build() {
        return this;
    }
}

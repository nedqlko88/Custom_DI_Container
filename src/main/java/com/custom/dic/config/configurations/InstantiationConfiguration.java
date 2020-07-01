package com.custom.dic.config.configurations;

import com.custom.dic.config.BaseSubConfiguration;
import com.custom.dic.config.Configuration;
import com.custom.dic.constants.Constants;

public class InstantiationConfiguration extends BaseSubConfiguration {

    private int maximumAllowedIterations;

    public InstantiationConfiguration(Configuration parentConfig) {
        super(parentConfig);
        this.maximumAllowedIterations = Constants.MAX_NUMBER_OF_INSTANTIATION_ITERATIONS;
    }

    public InstantiationConfiguration setMaximumNumberOfAllowedIterations(int num) {
        this.maximumAllowedIterations = num;
        return this;
    }

    public int getMaximumAllowedIterations() {
        return this.maximumAllowedIterations;
    }
}

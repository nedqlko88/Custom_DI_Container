package com.custom.dic.test;


import com.custom.dic.annotations.Autowired;
import com.custom.dic.annotations.PostConstruct;
import com.custom.dic.annotations.PreDestroy;
import com.custom.dic.annotations.Service;

@Service
public class TestServiceTwo {
    private final TestServiceOne testServiceOne;

    @Autowired
    public TestServiceTwo(TestServiceOne testServiceOne) {
        this.testServiceOne = testServiceOne;
    }

    @PostConstruct
    private void onInit() {

    }

    @PreDestroy
    private void onDestroy() {

    }

}

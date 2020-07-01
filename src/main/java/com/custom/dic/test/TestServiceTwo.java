package com.custom.dic.test;


import com.custom.dic.annotations.*;

import javax.print.attribute.standard.MediaSize;

@Service
public class TestServiceTwo {

    private final TestServiceOne testServiceOne;


    public TestServiceTwo(TestServiceOne testServiceOne) {
        this.testServiceOne = testServiceOne;
    }


    @PostConstruct
    private void onInit() {
        System.out.println("testing post for service 2");
    }

    @PreDestroy
    private void onDestroy() {

    }

    @Bean
    public OtherService otherService() {
        System.out.println("the bean is instantiated");
        return new OtherService();
    }

}

package com.custom.dic.test;


import com.custom.dic.annotations.*;

import javax.print.attribute.standard.MediaSize;

@Service
public class TestServiceTwo {
    private final TestServiceOne testServiceOne;


//    @Autowired
    public TestServiceTwo() {
        this.testServiceOne = null;
    }
    public TestServiceTwo(TestServiceOne testServiceOne) {
        this.testServiceOne = testServiceOne;
    }

    @PostConstruct
    private void onInit() {

    }

    @PreDestroy
    private void onDestroy() {

    }

    @Bean
    public OtherService otherService() {
        return new OtherService();
    }

}

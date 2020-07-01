package com.custom.dic.test;

import com.custom.dic.annotations.PostConstruct;
import com.custom.dic.annotations.Service;

@Service
public class TestServiceOne {

    private final OtherService otherService;

    public TestServiceOne(OtherService otherService) {
        this.otherService = otherService;
    }


    @PostConstruct
    public void init() {
        System.out.println("Hello from serviceOne " + this.otherService.getClass().getName());
    }
}

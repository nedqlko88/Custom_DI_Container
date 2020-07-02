package com.custom.dic.test;

import com.custom.dic.annotations.Autowired;
import com.custom.dic.annotations.Bean;
import com.custom.dic.annotations.PostConstruct;
import com.custom.dic.annotations.Service;

@CustomService
public class TestServiceOne {

    public TestServiceOne() {

    }

@CustomBean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

//    @PostConstruct
//    public void init() {
//        System.out.println("Hello from serviceOne ");
//    }
}

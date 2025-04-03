package com.meta.facebook;

import org.springframework.stereotype.Component;


public class MyFirstClass {

    private final String myVar;

    public MyFirstClass(String myVar) {
        this.myVar = myVar;
    }

    public String sayHello(){
        return "Hello World ==> myVar = " + myVar ;
    }
}

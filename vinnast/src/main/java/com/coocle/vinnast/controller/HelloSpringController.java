package com.coocle.vinnast.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSpringController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Spring 2024";
    }
}

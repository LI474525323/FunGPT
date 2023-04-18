package com.gtp.demo.controller;

import com.gtp.demo.interfaces.LimitRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    @LimitRequest(count=2)
    public String handle01(){
        System.out.println("hello");
        return "Hello, Spring Boot 2!";
    }
}
package com.gtp.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class MyController {

    @GetMapping("/login")
    public String test(){
        System.out.println("test");
        return "login";
    }
}
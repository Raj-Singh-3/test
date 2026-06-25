package com.rs.portfolio.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    @Value("${NAME}")
    private String name;

    @GetMapping("/hello")
    public String hello() {
        System.out.println(name);
        return name;
    }
}
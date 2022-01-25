package com.example.springproject.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;

@RestController
@RequestMapping
public class TestController {

    @GetMapping("/test")
    public String test(Principal principal){
        return "Hello " + principal.getName();
    }


}

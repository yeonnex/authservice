package com.example.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpApiController {
    @GetMapping("/home")
    public String home(){
        return "home";
    }


}

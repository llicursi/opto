package com.llicursi.opto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class TestController {

    @GetMapping("/api/v1/test")
    public String print(){
        return "Errorrrrrrr, security is empty";
    }

    @GetMapping("/test")
    public String print2(){
        return "Errorrrrrrr, security is empty";
    }
}

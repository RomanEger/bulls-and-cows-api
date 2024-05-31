package org.example.bullsandcowsapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class Home {

    @GetMapping("/hello")
    public String Hello(){
        return "Hello world!";
    }
}

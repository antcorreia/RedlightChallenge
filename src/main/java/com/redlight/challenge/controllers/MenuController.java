package com.redlight.challenge.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/")
    public String empty(){
        return "menu";
    }

    @GetMapping("/menu")
    public String menu() { return "menu"; }
}

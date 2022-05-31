package com.redlight.challenge.controllers;

import com.redlight.challenge.services.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CocktailController {

    @Autowired
    CocktailService cocktailService;

}

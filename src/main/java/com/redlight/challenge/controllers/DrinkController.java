package com.redlight.challenge.controllers;

import com.redlight.challenge.data.Drink;
import com.redlight.challenge.forms.FormDrink;
import com.redlight.challenge.services.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class DrinkController {

    @Autowired
    DrinkService drinkService;

    String result = "\n";

    @GetMapping("/newdrink")
    public String newdrink(Model m){
        m.addAttribute("result", result);
        m.addAttribute("drink", new FormDrink());
        result = "\n";

        return "newdrink";
    }

    @PostMapping("/savedrink")
    public String savedrink(@ModelAttribute("drink") FormDrink fd){
        Optional<String> name = drinkService.checkName(fd.getName());
        if (name.isEmpty()){
            drinkService.createDrink(fd.getName(), fd.getQuantity(), fd.getImage_url());

            result = "Drink created successfully";
        }
        else result = "A drink with that name already exists";

        return "redirect:/newdrink";
    }
}

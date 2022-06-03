package com.redlight.challenge.controllers;

import com.redlight.challenge.data.Drink;
import com.redlight.challenge.data.DrinkCocktail;
import com.redlight.challenge.forms.FormCocktail;
import com.redlight.challenge.services.CocktailService;
import com.redlight.challenge.services.DrinkCocktailService;
import com.redlight.challenge.services.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;
import java.util.stream.IntStream;

@Controller
public class CocktailController {

    @Autowired
    CocktailService cocktailService;

    @Autowired
    DrinkService drinkService;

    @Autowired
    DrinkCocktailService drinkCocktailService;

    String result = "\n";

    @GetMapping("/newcocktail")
    public String newcocktail(Model m){
        int drinkCount = drinkService.getDrinkCount();
        List<Integer> nList = new ArrayList<>();
        for (int i = 2; i <= drinkCount; i++) nList.add(i);
        m.addAttribute("n_drinks", nList);

        String[] drinks = drinkService.getAllDrinkNames();
        m.addAttribute("drink_list", drinks);

        m.addAttribute("cocktail", new FormCocktail());
        m.addAttribute("result", result);
        result = "\n";

        return "newcocktail";
    }

    @PostMapping("/createcocktail")
    public String createcocktail(@ModelAttribute("cocktail") FormCocktail fc){
        if (fc.getName().equals(""))
            result = "The cocktail must have a name";
        else if (fc.getNumber_of_drinks() == 0)
            result = "Please select how many drinks are used";
        else if (fc.getDrinks().equals(""))
            result = "Please select drinks to be used";
        else{
            String[] drinks = fc.getDrinks().split(" / / ");
            if (drinks.length != fc.getNumber_of_drinks())
                result = "Every dropdown must have a drink";
            else
                for (String drink : drinks)
                    if (fc.getDrinks().split(drink).length != 2) {
                        result = "There can't be duplicates in the drinks";
                        break;
                    }
        }

        if (cocktailService.checkName(fc.getName()).isEmpty()){
            cocktailService.createCocktail(fc.getName());

            int cocktailId = cocktailService.getCocktailIdByName(fc.getName());
            String[] drinks = fc.getDrinks().split(" / / ");
            for (int i = 0; i < fc.getNumber_of_drinks(); i++) {
                int drinkId = drinkService.getDrinkIdByName(drinks[i]);
                drinkCocktailService.createDrinkCocktail(drinkId, cocktailId);
            }

            result = "Cocktail created successfully";
        }
        else result = "A cocktail with that name already exists";

        return "redirect:/newcocktail";
    }
}

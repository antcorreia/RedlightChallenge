package com.redlight.challenge.controllers;

import com.redlight.challenge.data.Drink;
import com.redlight.challenge.forms.FormDrink;
import com.redlight.challenge.services.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
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

    @PostMapping("/createdrink")
    public String createdrink(@ModelAttribute("drink") FormDrink fd){
        Optional<String> name = drinkService.checkName(fd.getName());
        if (name.isEmpty()){
            drinkService.createDrink(fd.getName(), fd.getQuantity(), fd.getImage_url());

            result = "Drink created successfully";
        }
        else result = "A drink with that name already exists";

        return "redirect:/newdrink";
    }

    @GetMapping("/drinks")
    public String drinks(Model m){
        List<Drink> drinks = drinkService.getAllDrinks();
        m.addAttribute("drinks", drinks);
        m.addAttribute("result", result);
        result = "\n";

        return "drinks";
    }

    @GetMapping("/drinks/{drink_id}")
    public String drink(@PathVariable("drink_id") int drink_id, Model m){
        Optional<Drink> drink = drinkService.getDrinkById(drink_id);
        drink.ifPresent(value -> m.addAttribute("drink", value));

        return "drink";
    }

    @GetMapping("drinks/{drink_id}/edit")
    public String editdrink(@PathVariable("drink_id") int drink_id, Model m){
        m.addAttribute("result", result);
        m.addAttribute("drink_id", drink_id);
        m.addAttribute("drink", new FormDrink());
        result = "\n";

        return "editdrink";
    }

    @PostMapping("drinks/{drink_id}/savedrink")
    public String savedrink(@PathVariable("drink_id") int drink_id, @ModelAttribute("drink") FormDrink fd) {
        if (drinkService.editDrink(drink_id, fd.getName(), fd.getQuantity(), fd.getImage_url())) {
            result = "Drink successfully edited";
            return "redirect:/drinks";
        }
        else{
            result = "A drink with that name already exists";
            return "redirect:/drinks/{drink_id}/edit";
        }
    }

    @GetMapping("drinks/{drink_id}/delete")
    public String confirmdelete(@PathVariable("drink_id") int drink_id, Model m){
        m.addAttribute("drink_name", drinkService.getDrinkNameById(drink_id));

        return "deletedrink";
    }

    @PostMapping("drinks/{drink_id}/deletedrink")
    public String deletedrink(@PathVariable("drink_id") int drink_id){
        String drinkName = drinkService.getDrinkNameById(drink_id);
        drinkService.deleteDrinkById(drink_id);
        result = "Successfully deleted " + drinkName;

        return "redirect:/drinks";
    }
}

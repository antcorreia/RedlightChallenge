package com.redlight.challenge.controllers;

import com.redlight.challenge.data.Cocktail;
import com.redlight.challenge.data.Drink;
import com.redlight.challenge.data.DrinkCocktail;
import com.redlight.challenge.forms.FormCocktail;
import com.redlight.challenge.forms.FormSellCocktail;
import com.redlight.challenge.services.CocktailService;
import com.redlight.challenge.services.DrinkCocktailService;
import com.redlight.challenge.services.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        if (fc.getName().equals("")) {
            result = "The cocktail must have a name";
            return "redirect:/newcocktail";
        }
        else if (fc.getNumber_of_drinks() == 0) {
            result = "Please select how many drinks are used";
            return "redirect:/newcocktail";
        }
        else if (fc.getDrinks().equals("")) {
            result = "Please select drinks to be used";
            return "redirect:/newcocktail";
        }
        else{
            String[] drinks = fc.getDrinks().split(" / / ");
            String[] quantities = fc.getQuantities().split(" ");
            if (drinks.length != fc.getNumber_of_drinks()) {
                result = "Every dropdown must have a drink";
                return "redirect:/newcocktail";
            }
            else
                for (String drink : drinks)
                    if (fc.getDrinks().split(drink).length != 2) {
                        result = "There can't be duplicates in the drinks";
                        return "redirect:/newcocktail";
                    }

            if (quantities.length != fc.getNumber_of_drinks()) {
                result = "Every drink must have a quantity";
                return "redirect:/newcocktail";
            }
            else
                for (String quantity : quantities) {
                    try {
                        int q = Integer.parseInt(quantity);
                        if (q < 1){
                            result = "Quantity must be bigger at least 1";
                            return "redirect:/newcocktail";
                        }
                    }
                    catch (Exception e){
                        result = "Quantity must be a valid number";
                        return "redirect:/newcocktail";
                    }
                }
        }

        if (cocktailService.checkName(fc.getName()).isEmpty()){
            cocktailService.createCocktail(fc.getName());

            int cocktailId = cocktailService.getCocktailIdByName(fc.getName());
            String[] drinks = fc.getDrinks().split(" / / ");
            String[] quantities = fc.getQuantities().split(" ");
            for (int i = 0; i < fc.getNumber_of_drinks(); i++) {
                int drinkId = drinkService.getDrinkIdByName(drinks[i]);
                drinkCocktailService.createDrinkCocktail(drinkId, cocktailId, Integer.parseInt(quantities[i]));
            }

            result = "Cocktail created successfully";
        }
        else result = "A cocktail with that name already exists";

        return "redirect:/newcocktail";
    }

    @GetMapping("/cocktails")
    public String cocktails(Model m){
        List<Cocktail> cocktails = cocktailService.getAllCocktails();
        m.addAttribute("cocktails", cocktails);

        List<Integer> quantities = new ArrayList<>();
        for (Cocktail cocktail : cocktails)
            quantities.add(drinkCocktailService.getQuantityById(cocktail.getCocktail_id()));
        m.addAttribute("quantities", quantities);

        m.addAttribute("result", result);
        result = "\n";

        return "cocktails";
    }

    @GetMapping("/cocktails/{cocktail_id}")
    public String cocktail(@PathVariable("cocktail_id") int cocktail_id, Model m){
        Cocktail cocktail = cocktailService.getCocktailById(cocktail_id);
        m.addAttribute("cocktail", cocktail);

        List<int[]> pairs = drinkCocktailService.getPairsById(cocktail_id);
        List<Integer> quantities =  new ArrayList<>();
        List<String> drinks = new ArrayList<>();
        List<Integer> ids = new ArrayList<>();
        for (int[] pair : pairs){
            quantities.add(pair[1]);
            drinks.add(drinkService.getDrinkNameById(pair[0]));
            ids.add(pair[0]);
        }
        m.addAttribute("quantities", quantities);
        m.addAttribute("drinks", drinks);
        m.addAttribute("ids", ids);

        m.addAttribute("result", result);
        result = "\n";

        return "cocktail";
    }

    @GetMapping("/cocktails/{cocktail_id}/sell")
    public String sell(@PathVariable("cocktail_id") int cocktail_id, Model m){
        m.addAttribute("cocktail_id", cocktail_id);
        m.addAttribute("sellcocktail", new FormSellCocktail());
        m.addAttribute("result", result);
        result = "\n";

        return "sellcocktail";
    }

    @PostMapping("/cocktails/{cocktail_id}/sellcocktail")
    public String sellcocktail(@PathVariable("cocktail_id") int cocktail_id, @ModelAttribute("sellcocktail") FormSellCocktail fsc){
        try {
            int amount = Integer.parseInt(fsc.getAmount());
            List<int[]> pairs = drinkCocktailService.getPairsById(cocktail_id);
            for (int[] pair : pairs) {
                if (drinkService.getDrinkById(pair[0]).get().getQuantity() - amount < 0) {
                    result = "There isn't enough stock of " + drinkService.getDrinkNameById(pair[0]) + " to sell " +
                            + amount + " cocktails";
                    return "redirect:/cocktails/{cocktail_id}/sell";
                }
            }
            for (int[] pair : pairs) {
                Drink drink = drinkService.getDrinkById(pair[0]).get();
                drink.setQuantity(drink.getQuantity() - amount * pair[1]);

                drinkService.editDrink(pair[0], drink.getName(), drink.getQuantity(), drink.getImage_url());
            }

            result = "Successfully sold " + amount + " of this cocktail";
            return "redirect:/cocktails/{cocktail_id}";
        }
        catch (Exception e){
            result = "Something went wrong";
            return "redirect:/cocktails/{cocktail_id}/sell";
        }
    }

    @GetMapping("/cocktails/{cocktail_id}/delete")
    public String delete(@PathVariable("cocktail_id") int cocktail_id, Model m){
        m.addAttribute("cocktail_name", cocktailService.getCocktailNameById(cocktail_id));

        return "deletecocktail";
    }

    @PostMapping("/cocktails/{cocktail_id}/deletecocktail")
    public String deletecocktail(@PathVariable("cocktail_id") int cocktail_id){
        String cocktailName = cocktailService.getCocktailNameById(cocktail_id);
        drinkCocktailService.deleteAllByCocktailId(cocktail_id);
        cocktailService.deleteCocktailById(cocktail_id);
        result = "Successfully deleted " + cocktailName;

        return "redirect:/cocktails";
    }

    @GetMapping("/cocktails/{cocktail_id}/edit")
    public String editcocktail(@PathVariable("cocktail_id") int cocktail_id, Model m){
        int drinkCount = drinkService.getDrinkCount();
        List<Integer> nList = new ArrayList<>();
        for (int i = 2; i <= drinkCount; i++) nList.add(i);
        m.addAttribute("n_drinks", nList);

        String[] drinks = drinkService.getAllDrinkNames();
        m.addAttribute("drink_list", drinks);

        m.addAttribute("cocktail_id", cocktail_id);
        m.addAttribute("cocktail", new FormCocktail());
        m.addAttribute("result", result);
        result = "\n";

        return "editcocktail";
    }

    @PostMapping("/cocktails/{cocktail_id}/savecocktail")
    public String savecocktail(@PathVariable("cocktail_id") int cocktail_id, @ModelAttribute("cocktail") FormCocktail fc){
        if (fc.getName().equals("")) {
            result = "The cocktail must have a name";
            return "redirect:/cocktails/{cocktail_id}/edit";
        }
        else if (fc.getNumber_of_drinks() == 0) {
            result = "Please select how many drinks are used";
            return "redirect:/cocktails/{cocktail_id}/edit";
        }
        else if (fc.getDrinks().equals("")) {
            result = "Please select drinks to be used";
            return "redirect:/cocktails/{cocktail_id}/edit";
        }
        else{
            String[] drinks = fc.getDrinks().split(" / / ");
            String[] quantities = fc.getQuantities().split(" ");
            if (drinks.length != fc.getNumber_of_drinks()) {
                result = "Every dropdown must have a drink";
                return "redirect:/cocktails/{cocktail_id}/edit";
            }
            else
                for (String drink : drinks)
                    if (fc.getDrinks().split(drink).length != 2) {
                        result = "There can't be duplicates in the drinks";
                        return "redirect:/cocktails/{cocktail_id}/edit";
                    }

            if (quantities.length != fc.getNumber_of_drinks()) {
                result = "Every drink must have a quantity";
                return "redirect:/cocktails/{cocktail_id}/edit";
            }
            else
                for (String quantity : quantities) {
                    try {
                        int q = Integer.parseInt(quantity);
                        if (q < 1){
                            result = "Quantity must be bigger at least 1";
                            return "redirect:/cocktails/{cocktail_id}/edit";
                        }
                    }
                    catch (Exception e){
                        result = "Quantity must be a valid number";
                        return "redirect:/cocktails/{cocktail_id}/edit";
                    }
                }
        }

        if (cocktailService.checkName(fc.getName()).isEmpty()){
            cocktailService.createCocktail(fc.getName());

            int cocktailId = cocktailService.getCocktailIdByName(fc.getName());
            String[] drinks = fc.getDrinks().split(" / / ");
            String[] quantities = fc.getQuantities().split(" ");
            for (int i = 0; i < fc.getNumber_of_drinks(); i++) {
                int drinkId = drinkService.getDrinkIdByName(drinks[i]);
                drinkCocktailService.createDrinkCocktail(drinkId, cocktailId, Integer.parseInt(quantities[i]));
            }

            result = "Cocktail edited successfully";
        }
        else{
            if (cocktailService.getCocktailIdByName(fc.getName()) != cocktail_id) {
                result = "A cocktail with that name already exists";
            }
            else{
                cocktailService.editCocktail(cocktail_id, fc.getName());
                drinkCocktailService.deleteAllByCocktailId(cocktail_id);

                String[] drinks = fc.getDrinks().split(" / / ");
                String[] quantities = fc.getQuantities().split(" ");
                for (int i = 0; i < fc.getNumber_of_drinks(); i++) {
                    int drinkId = drinkService.getDrinkIdByName(drinks[i]);
                    drinkCocktailService.createDrinkCocktail(drinkId, cocktail_id, Integer.parseInt(quantities[i]));
                }

                result = "Cocktail edited successfully";
            }
        }

        return "redirect:/cocktails/{cocktail_id}/edit";
    }
}

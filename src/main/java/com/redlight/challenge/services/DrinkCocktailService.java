package com.redlight.challenge.services;

import com.redlight.challenge.data.DrinkCocktail;
import com.redlight.challenge.repository.DrinkCocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrinkCocktailService {
    @Autowired
    DrinkCocktailRepository drinkCocktailRepository;

    public void createDrinkCocktail(int drink_id, int cocktail_id, int quantity){
        DrinkCocktail drinkCocktail = new DrinkCocktail();
        drinkCocktail.setDrink_id(drink_id);
        drinkCocktail.setCocktail_id(cocktail_id);
        drinkCocktail.setQuantity(quantity);

        drinkCocktailRepository.save(drinkCocktail);
    }

    public int getQuantityById(int cocktail_id){
        return drinkCocktailRepository.getQuantityById(cocktail_id);
    }

    public List<int[]> getPairsById(int cocktail_id){
        return drinkCocktailRepository.getPairsById(cocktail_id);
    }
}

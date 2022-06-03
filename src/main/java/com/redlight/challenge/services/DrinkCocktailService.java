package com.redlight.challenge.services;

import com.redlight.challenge.data.DrinkCocktail;
import com.redlight.challenge.repository.DrinkCocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinkCocktailService {
    @Autowired
    DrinkCocktailRepository drinkCocktailRepository;

    public void createDrinkCocktail(int drink_id, int cocktail_id){
        DrinkCocktail drinkCocktail = new DrinkCocktail();
        drinkCocktail.setDrink_id(drink_id);
        drinkCocktail.setCocktail_id(cocktail_id);

        drinkCocktailRepository.save(drinkCocktail);
    }
}

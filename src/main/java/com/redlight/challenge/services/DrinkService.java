package com.redlight.challenge.services;

import com.redlight.challenge.data.Drink;
import com.redlight.challenge.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DrinkService {

    @Autowired
    DrinkRepository drinkRepository;

    public Optional<String> checkName(String name){
        return drinkRepository.checkName(name);
    }

    public void createDrink(String name, int quantity, String image_url) {
        Drink drink = new Drink();
        drink.setName(name);
        drink.setQuantity(quantity);
        drink.setImage_url(image_url);

        drinkRepository.save(drink);
    }
}

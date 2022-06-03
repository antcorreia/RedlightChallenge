package com.redlight.challenge.services;

import com.redlight.challenge.data.Drink;
import com.redlight.challenge.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DrinkService {

    @Autowired
    DrinkRepository drinkRepository;

    public Optional<String> checkName(String name){
        return drinkRepository.checkName(name);
    }

    public List<Drink> getAllDrinks(){
        List<Drink> drinks = new ArrayList<>();
        drinkRepository.findAll().forEach(drinks::add);

        return drinks;
    }

    public Optional<Drink> getDrinkById(int drink_id){
        return drinkRepository.findById(drink_id);
    }

    public String getDrinkNameById(int drink_id){
        return drinkRepository.getDrinkNameById(drink_id);
    }

    public void createDrink(String name, int quantity, String image_url) {
        Drink drink = new Drink();
        drink.setName(name);
        drink.setQuantity(quantity);
        drink.setImage_url(image_url);

        drinkRepository.save(drink);
    }

    public boolean editDrink(int drink_id, String name, int quantity, String image_url) {
        if(drinkRepository.findById(drink_id).isPresent()) {
            Drink drink = drinkRepository.findById(drink_id).get();
            if (drinkRepository.checkName(name).isPresent() &&
               !drinkRepository.checkName(name).get().equals(drink.getName()))
                return false;

            drink.setName(name);
            drink.setQuantity(quantity);
            drink.setImage_url(image_url);

            drinkRepository.save(drink);
            return true;
        }

        return false;
    }

    public void deleteDrinkById(int drink_id){
        drinkRepository.deleteById(drink_id);
    }

    public int getDrinkCount(){
        return drinkRepository.getDrinkCount();
    }

    public String[] getAllDrinkNames(){
        return drinkRepository.getAllDrinkNames();
    }

    public int getDrinkIdByName(String name){
        return drinkRepository.getDrinkIdByName(name);
    }
}

package com.redlight.challenge.services;

import com.redlight.challenge.data.Cocktail;
import com.redlight.challenge.repository.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CocktailService {

    @Autowired
    CocktailRepository cocktailRepository;

    public Optional<String> checkName(String name){
        return cocktailRepository.checkName(name);
    }

    public void createCocktail(String name){
        Cocktail cocktail = new Cocktail();
        cocktail.setName(name);

        cocktailRepository.save(cocktail);
    }

    public int getCocktailIdByName(String name){
        return cocktailRepository.getCocktailIdByName(name);
    }

    public List<Cocktail> getAllCocktails(){
        List<Cocktail> cocktails = new ArrayList<>();
        cocktailRepository.findAll().forEach(cocktails::add);

        return cocktails;
    }

    public Cocktail getCocktailById(int cocktail_id){
        return cocktailRepository.getCocktailById(cocktail_id);
    }
}
